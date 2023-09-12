package com.study.seckillboot.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午4:23
 * @menu
 */
@ConfigurationProperties(prefix="spring.redis", ignoreUnknownFields = false)
@Data
@ToString
public class RedisProperties {

    private int database;

    /**
     * 等待節點回覆命令的時間。該時間從命令發送成功時開始計時
     */
    private int timeout;

    private String password;

    private String mode;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 單機信息配置
     */
    private RedisSingleProperties single;

    /**
     * 集羣 信息配置
     */
    private RedisClusterProperties cluster;

    /**
     * 哨兵配置
     */
    private RedisSentinelProperties sentinel;
}
