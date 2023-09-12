package com.study.seckillboot.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 哨兵配置
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午4:29
 * @menu 哨兵配置
 */
@Data
@ToString
public class RedisSentinelProperties {
    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private Boolean masterOnlyWrite;

    private Integer failMax;
}
