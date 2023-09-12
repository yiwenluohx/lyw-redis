package com.study.seckillboot.config;

import lombok.Data;
import lombok.ToString;

/**
 * 单节点配置
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午4:26
 * @menu 单节点配置
 */
@Data
@ToString
public class RedisSingleProperties {

    private String address;
}
