package com.study.seckillboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.seckillboot.model.SeckillInfo;
import com.study.seckillboot.model.SeckillOrder;
import com.study.seckillboot.model.SeckillSkuInfo;
import com.study.seckillboot.service.SecKillService;
import com.study.seckillboot.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
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

    @PostConstruct
    public void initData() {
        redisUtils.setStr("test", "123");
//        // 缓存秒杀活动 (key: seckill:demo)
//        BoundHashOperations<String, String, Object> seckills = stringRedisTemplate.boundHashOps("seckill:demo");
//        seckills.put(seckillInfo.getId(), JSON.toJSONString(seckillInfo));
//// 缓存商品信息 (key: seckill:skus:+秒杀活动id)
//        BoundHashOperations<String, String, Object> skus = stringRedisTemplate.boundHashOps("seckill:skus:" + seckillInfo.getId());
//// 通过秒杀活动id查询秒杀活动商品列表
//        List<SeckillSkuInfo> skuInfos = seckillSkuInfoService.listBySecKillId(seckillInfo.getId());
//        skuInfos.forEach(item -> {
//            String json = JSON.toJSONString(item);
//            // 保存每个sku信息
//            skus.put(item.getId(), json);
//            // 设置分布式信号量 (key: seckill:stock:+秒杀商品表id)
//            RSemaphore semaphore = redissonClient.getSemaphore("seckill:stock:" + item.getId());
//            // 信号量设置秒杀商品库存
//            semaphore.trySetPermits(item.getSeckillStock());
//            // 设置缓存过期时间到秒杀结束时间
//            stringRedisTemplate.expire("seckill:stock:" + item.getId(), seckillInfo.getEndTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
//        });
    }


    @Override
    public void useRedisList(int id) {
        log.info("当前线程名称{}", Thread.currentThread().getName());
//        // 获取秒杀活动
//        BoundHashOperations<String, String, Object> seckills = stringRedisTemplate.boundHashOps("seckill:demo");
//// 获取秒杀活动信息
//        SeckillInfo info = JSON.parseObject(seckills.get(seckillId).toString(), SeckillInfo.class);
//// 相关判断逻辑省略
//// 获取秒杀商品信息
//        BoundHashOperations<String, String, String> skus = stringRedisTemplate.boundHashOps("seckill:skus:" + seckillId);
//        String skuInfoStr = skus.get(id);// id为前端秒杀传回的商品表id
//        if (StringUtils.isNotBlank(skuInfoStr)) {
//            SeckillSkuInfo skuInfo = JSON.parseObject(skuInfoStr, SeckillSkuInfo.class);
//            // 获取信号量
//            RSemaphore semaphore = redissonClient.getSemaphore("seckill:stock:" + skuInfo.getId());
//            // 判断是否还有库存
//            int count = semaphore.availablePermits();
//            if (count == 0) {
//                // 已抢光
//            }
//            // 这个就是不阻塞，能拿到信号量就为true，拿不到（信号量为0 了）就是false
//            boolean flag = semaphore.tryAcquire();
//            if (flag) {
//                // 获取用户成功秒杀次数，若key存在则加1，不存在则初始化为0后再加1
//                long total = stringRedisTemplate.opsForValue().increment("seckill:user:" + userId + "_" + info.getId(), 1);
//                //设置过期时间为活动有效时间
//                stringRedisTemplate.expire("seckill:user:" + userId + "_" + info.getId(), endTime - time, TimeUnit.MILLISECONDS);
//                // 判断抢到的用户是否满足要求，不满足则释放一个信号量（redis中信号量值+1）
//                if (不满足条件) {
//                    // 释放信号量 ，其中num为购买商品数量
//                    semaphore.release(num);
//                }
//                // 秒杀成功,设置订单信息
//                SeckillOrder order = new SeckillOrder();
//                // 保存到mq
//                rabbitTemplate.convertAndSend("seckillExchange", "seckillOrderRoutingkey", order);
//            } else {
//                // 秒杀失败
//            }
//
//        } else {
//            // 秒杀失败
//        }
    }
}
