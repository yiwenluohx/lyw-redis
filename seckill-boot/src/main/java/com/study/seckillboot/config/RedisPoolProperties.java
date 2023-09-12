package com.study.seckillboot.config;

import lombok.Data;
import lombok.ToString;

/**
 * redis连接池
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午4:25
 * @menu
 */
@Data
@ToString
public class RedisPoolProperties {
    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;
}
