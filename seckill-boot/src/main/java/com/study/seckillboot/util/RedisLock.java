package com.study.seckillboot.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redisson实现分布式锁
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/13 下午4:50
 * @menu redisson实现分布式锁
 */
@Component
public class RedisLock {

    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取锁 不等待
     *
     * @param lockKey
     * @param handleFun
     * @return {@link T}
     */
    public <T> T syncHandle(String lockKey, Supplier<T> handleFun) {
        return syncLockHandle(lockKey, 0, handleFun, "");
    }


    /**
     * 获取锁 不等待
     *
     * @param lockKey
     * @param handleFun
     * @param errorMsg
     * @return {@link T}
     */
    public <T> T syncHandle(String lockKey, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, 0, handleFun, errorMsg);
    }


    /**
     * 获取锁 等待 waitMilli 单位毫秒
     *
     * @param lockKey
     * @param waitMilli
     * @param handleFun
     * @param errorMsg
     * @return {@link T}
     */
    public <T> T syncHandle(String lockKey, long waitMilli, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, waitMilli, handleFun, errorMsg);
    }


    /**
     * 获取锁 一直等待
     *
     * @param lockKey
     * @param handleFun
     * @param errorMsg
     * @return {@link T}
     */
    public <T> T syncWaitHandle(String lockKey, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, -1, handleFun, errorMsg);
    }


    /**
     * trylock()和lock()的区别：trylock会立即返回，锁获取成功返回true否则返回false。lock没有返回，会一直阻塞直到锁获取成功
     *
     * @param lockKey
     * @param waitMilli
     * @param handleFun
     * @param errorMsg
     * @return {@link T}
     */
    private <T> T syncLockHandle(String lockKey, long waitMilli, Supplier<T> handleFun, String errorMsg) {
        RLock rLock = null;
        T result;
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = "{" + lockKey + "}->get lock error";
        }
        boolean acquired = true;
        try {
            rLock = this.redissonClient.getLock(lockKey);
            if (rLock == null) {
                throw new UnsupportedOperationException(errorMsg);
            }
            // 一直阻滞
            if (waitMilli < 0) {
                rLock.lock();
            } else if (waitMilli > 0) {
                //等待多少秒
                acquired = rLock.tryLock(waitMilli, TimeUnit.MILLISECONDS);
            } else {
                //不等待
                acquired = rLock.tryLock();
            }
            if (!acquired) {
                throw new UnsupportedOperationException(errorMsg);
            }
            result = handleFun.get();
        } catch (UnsupportedOperationException e1) {
            throw e1;
        } catch (Throwable e) {
            log.error("", e);
            throw new UnsupportedOperationException(errorMsg);
        } finally {
            if (rLock != null && acquired) {
                rLock.unlock();
            }
        }
        return result;
    }
}