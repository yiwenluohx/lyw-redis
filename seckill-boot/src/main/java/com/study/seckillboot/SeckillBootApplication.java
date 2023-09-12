package com.study.seckillboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luohongxiao
 */

@SpringBootApplication
@MapperScan("com.study.seckillboot.dao")
public class SeckillBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillBootApplication.class, args);
	}

}
