package com.example.ylbtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//开启定时任务注解
@EnableScheduling
public class YlbTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(YlbTaskApplication.class, args);
    }

}
