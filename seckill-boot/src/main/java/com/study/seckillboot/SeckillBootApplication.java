package com.study.seckillboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luohongxiao
 */

@SpringBootApplication(scanBasePackages = {"com.study.seckillboot" })
public class SeckillBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillBootApplication.class, args);
	}

}
