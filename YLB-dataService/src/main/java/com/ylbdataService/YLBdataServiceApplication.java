package com.ylbdataService;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.ylbdataService.mapper")
@EnableCaching
public class YLBdataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(YLBdataServiceApplication.class);
    }
}
