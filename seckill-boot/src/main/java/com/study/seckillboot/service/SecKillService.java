package com.study.seckillboot.service;

/**
 * 秒杀
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 上午11:06
 * @menu 秒杀
 */
public interface SecKillService {

    /**
     * 使用redis的信号量完成秒杀
     *
     * @param id
     * @param userId
     */
    void useRedisSemaphore(String id, String userId);


    /**
     * 分布式锁实现秒杀
     */
    void useRedisLock();
}
