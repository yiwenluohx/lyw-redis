package com.study.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2021/6/16 0016 17:42
 */
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping()
    public String testRedis() {
        redisTemplate.opsForValue().set("name", "lucy");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }
}
