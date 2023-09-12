package com.study.seckillboot.controller;

import com.study.seckillboot.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 秒杀
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 上午10:59
 * @menu 秒杀
 */
@RequestMapping("/demo")
@RestController
public class SeckillController {

    @Autowired
    private SecKillService secKillService;

    @GetMapping("/test")
    public String secKill_01() {
        //模拟多线程抢购
        for (int i = 0; i < 110; i++) {
            Thread thread = new Thread(() -> secKillService.useRedisList("1", UUID.randomUUID().toString()));
            thread.setName("线程" + i);
            thread.start();
        }
        return "";
    }
}
