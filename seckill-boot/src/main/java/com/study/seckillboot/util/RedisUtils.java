package com.study.seckillboot.util;

import com.alibaba.fastjson.JSON;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午4:16
 * @menu
 */
@Component
public class RedisUtils {

    private final static Logger log = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 默认缓存时间
     */
    private static final Long DEFAULT_EXPIRED = 32000L;

    /**
     * 自动装配redisson client对象
     */
    @Resource
    private RedissonClient redissonClient;

    /**
     * 用于操作key
     *
     * @return RKeys 对象
     */
    public RKeys getKeys() {
        return redissonClient.getKeys();
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    /**
     * 获取getBuckets 对象
     *
     * @return RBuckets 对象
     */
    public RBuckets getBuckets() {
        return redissonClient.getBuckets();
    }

    /**
     * 读取缓存中的字符串，永久有效
     *
     * @param key 缓存key
     * @return 字符串
     */
    public String getStr(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 缓存字符串
     *
     * @param key
     * @param value
     */
    public void setStr(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 设置
     *
     * @param key
     * @param value
     */
    public void setAtomicLong(String key, Long value) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.addAndGet(value);
    }

    /**
     * 获取
     *
     * @param key
     */
    public Long getAtomicLong(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        return atomicLong.get();
    }

    /**
     * 获取
     *
     * @param key
     */
    public RAtomicLong getRAtomicLong(String key) {
        return redissonClient.getAtomicLong(key);
    }

    /**
     * 缓存带过期时间的字符串
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间，long类型，必须传值
     */
    public void setStr(String key, String value, long expired) {
        RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        bucket.set(value, expired <= 0L ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * string 操作，如果不存在则写入缓存（string方式，不带有redisson的格式信息）
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间
     */
    public Boolean setIfAbsent(String key, String value, long expired) {
        RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        return bucket.trySet(value, expired <= 0L ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * 如果不存在则写入缓存（string方式，不带有redisson的格式信息），永久保存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    public Boolean setIfAbsent(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        return bucket.trySet(value);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return true 存在
     */
    public Boolean isExists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 获取RList对象
     *
     * @param key RList的key
     * @return RList对象
     */
    public <T> RList<T> getList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 获取RMapCache对象
     *
     * @param key
     * @return RMapCache对象
     */
    public <K, V> RMapCache<K, V> getMap(String key) {
        return redissonClient.getMapCache(key);
    }

    /**
     * 获取RSET对象
     *
     * @param key
     * @return RSET对象
     */
    public <T> RSet<T> getSet(String key) {
        return redissonClient.getSet(key);
    }

    /**
     * 获取RScoredSortedSet对象
     *
     * @param key
     * @param <T>
     * @return RScoredSortedSet对象
     */
    public <T> RScoredSortedSet<T> getScoredSortedSet(String key) {
        return redissonClient.getScoredSortedSet(key);
    }

    /**
     * 在队列头部新增对象
     *
     * @param key
     * @param args
     */
    public void addOnDequeFirst(String key, Collection<String> args) {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(key);
        try {
            blockingDeque.putFirst(JSON.toJSONString(args));
            log.info("将消息: {} 插入到队列。", args);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在队列尾部减少一个对象
     *
     * @param key
     */
    public String removeOneOnDequeLast(String key) {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(key);
        try {
            return blockingDeque.pollLast();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取/设置分布式信号量
     *
     * @param key
     * @param stock
     * @return {@link RSemaphore}
     */
    public RSemaphore getSemaphore(String key, int stock) {
        // 设置分布式信号量 (key: seckill:stock:+秒杀商品表id)
        RSemaphore semaphore = redissonClient.getSemaphore(key);
        if (stock > 0) {
            // 信号量设置秒杀商品库存
            semaphore.trySetPermits(stock);
        }
        return semaphore;
    }


}
