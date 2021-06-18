package com.enesource.jump.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@MapperScan(value="com.enesource.jump.web.dao")
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableSwagger2
public class WebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
} 
