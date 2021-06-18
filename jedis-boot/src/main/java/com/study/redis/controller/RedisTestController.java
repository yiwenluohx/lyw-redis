package com.study.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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

    @GetMapping("/test")
    public String testRedis() {
        redisTemplate.opsForValue().set("name", "lucy");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }

    @GetMapping("testLock")
    public void testLock() {
        //1 获取锁，setne
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "111", 3, TimeUnit.SECONDS);
        //2 获取锁成功，查询num的值
        if (lock) {
            Object value = redisTemplate.opsForValue().get("num");
            //2.1 判断num为空return
            if (StringUtils.isEmpty(value)) {
                return;
            }
            //2.2 有值就转成int
            int num = Integer.parseInt(value + "");
            //2.3 把redis的num加1
            redisTemplate.opsForValue().set("num", ++num);
            //2.4 释放锁，del
            redisTemplate.delete("lock");
        } else {
            //3 获取锁失败、每隔0.1秒再获取
            try {
                Thread.sleep(100);
                testLock();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

}
