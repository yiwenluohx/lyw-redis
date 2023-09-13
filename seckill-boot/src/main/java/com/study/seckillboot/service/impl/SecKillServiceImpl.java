package com.study.seckillboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.study.seckillboot.dao.SeckillInfoMapper;
import com.study.seckillboot.dao.SeckillOrderMapper;
import com.study.seckillboot.dao.SeckillSkuInfoMapper;
import com.study.seckillboot.model.SeckillInfo;
import com.study.seckillboot.model.SeckillOrder;
import com.study.seckillboot.model.SeckillSkuInfo;
import com.study.seckillboot.service.SecKillService;
import com.study.seckillboot.util.RedisLock;
import com.study.seckillboot.util.RedisUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RSemaphore;
import org.redisson.api.RSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 上午11:08
 * @menu 秒杀
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    private final static Logger log = LoggerFactory.getLogger(SecKillServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillInfoMapper seckillInfoMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillSkuInfoMapper seckillSkuInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisLock redisLock;

    //    @PostConstruct
    public void initData() {
        SeckillInfo seckillInfo = seckillInfoMapper.selectById("1");
        // 缓存秒杀活动 (key: seckill:demo)
        BoundHashOperations<String, String, Object> seckills = stringRedisTemplate.boundHashOps("seckill:demo");
        seckills.put(seckillInfo.getId(), JSON.toJSONString(seckillInfo));
        // 缓存商品信息 (key: seckill:skus:+秒杀活动id)
        BoundHashOperations<String, String, Object> skus = stringRedisTemplate.boundHashOps("seckill:skus:" + seckillInfo.getId());
        // 通过秒杀活动id查询秒杀活动商品列表
        List<SeckillSkuInfo> skuInfos = seckillSkuInfoMapper.querySkuInfoBySecKillId(seckillInfo.getId());
        skuInfos.forEach(item -> {
            String json = JSON.toJSONString(item);
            // 保存每个sku信息
            skus.put(item.getId(), json);
            redisUtils.getSemaphore("seckill:stock:" + item.getId(), item.getSeckillStock());
            // 设置缓存过期时间到秒杀结束时间
            stringRedisTemplate.expire("seckill:stock:" + item.getId(), seckillInfo.getEndTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        });
    }

    @PostConstruct
    public void initData0() {
        SeckillSkuInfo skuInfo = seckillSkuInfoMapper.selectById("1");
        redisUtils.setAtomicLong("scekill:goods:" + skuInfo.getId(), Long.valueOf(skuInfo.getSeckillStock()));
    }


    @Override
    public void useRedisSemaphore(String seckillId, String userId) {
        log.info("秒杀开始，当前线程名称{}", Thread.currentThread().getName());
        // 获取秒杀活动
        BoundHashOperations<String, String, Object> seckills = stringRedisTemplate.boundHashOps("seckill:demo");
        // 获取秒杀活动信息
        SeckillInfo info = JSON.parseObject(seckills.get(seckillId).toString(), SeckillInfo.class);
        // 相关判断逻辑省略
        //获取秒杀商品信息
        BoundHashOperations<String, String, String> skus = stringRedisTemplate.boundHashOps("seckill:skus:" + seckillId);
        // id为前端秒杀传回的商品表id
        String skuInfoStr = skus.get(seckillId);
        if (StringUtils.isBlank(skuInfoStr)) {
            throw new UnsupportedOperationException("商品id不存在");
        }
        SeckillSkuInfo skuInfo = JSON.parseObject(skuInfoStr, SeckillSkuInfo.class);
        // 获取信号量
        RSemaphore semaphore = redisUtils.getSemaphore("seckill:stock:" + skuInfo.getId(), 0);
        log.info("还有多少个坑位num=" + semaphore.availablePermits());
        // 判断是否还有库存
        if (semaphore.availablePermits() == 0) {
            // 已抢光
            log.info("已抢光");
//            throw new UnsupportedOperationException("已抢光");
        } else {
            // 这个就是不阻塞，能拿到信号量就为true，拿不到（信号量为0 了）就是false
            boolean flag = semaphore.tryAcquire();
            if (!flag) {
                throw new UnsupportedOperationException("操作失败，请重试！");
            } else {
                // 获取用户成功秒杀次数，若key存在则加1，不存在则初始化为0后再加1
                long total = stringRedisTemplate.opsForValue().increment("seckill:user:" + userId + "_" + info.getId(), 1);
                //设置过期时间为活动有效时间
                stringRedisTemplate.expire("seckill:user:" + userId + "_" + info.getId(), 168000, TimeUnit.MILLISECONDS);
                // 判断抢到的用户是否满足要求，不满足则释放一个信号量（redis中信号量值+1）
                if (total > skuInfo.getLimitNum()) {
                    // 释放信号量 ，其中num为购买商品数量
                    semaphore.release(1);
                }
                log.info("秒杀结束，当前线程名称{}", Thread.currentThread().getName());
                //todo： 秒杀成功,设置订单信息，并推送到mq
//        SeckillOrder order = new SeckillOrder();
//        rabbitTemplate.convertAndSend("seckillExchange", "seckillOrderRoutingkey", order);
            }
        }
    }

    @Override
    public void useRedisLock() {
        redisLock.syncWaitHandle("scekill:goods:" + "1_1", () -> {

            RAtomicLong rAtomicLong = redisUtils.getRAtomicLong("scekill:goods:" + 1);
//            RSet<String> productUserSet = redissonClient.getSet(PRODUCT_USER_KEY);
//            if (productUserSet != null && productUserSet.contains(userId)) {
//                return "您已秒杀过，请勿重复秒杀";
//            }
            if (rAtomicLong.get() <= 0) {
                log.info("秒杀失败，当前线程名称{}", Thread.currentThread().getName());
                return "很遗憾，秒杀已结束";
            }
            // 加入到秒杀集合
//            productUserSet.add(userId);
            // 库存减1
            rAtomicLong.decrementAndGet();
            log.info("秒杀成功,库存剩余"+ rAtomicLong.get() +"当前线程名称{}", Thread.currentThread().getName());
            return "恭喜您，秒杀成功！";
        }, "网络繁忙，请重试");
    }
}
