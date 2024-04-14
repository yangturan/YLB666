package com.example.ylbweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.*;

@Configuration
public class ThreadConfig {
    @Bean
    public ThreadPoolTaskScheduler getScheduledThreadPool(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(3);
        return threadPoolTaskScheduler;
    }

    @Bean
//    自定义线程池
    public ThreadPoolExecutor getMyThreadPoll(){
        ThreadPoolExecutor pool=new ThreadPoolExecutor(
                6,//固定线程
                20,//最大线程
                60,//超时线程自毁时间
                TimeUnit.SECONDS,//时间单位
                new ArrayBlockingQueue<>(10),//长度有限的阻塞队列
                Executors.defaultThreadFactory(),//设置线程工厂帮助你在需要时创新新线程
                new ThreadPoolExecutor.AbortPolicy()//默认拒绝策略，拒绝任务并抛出异常
                //还有Discard决绝任务，DiacardOldest抛弃阻塞队列最老的任务执行新的任务，CallRun调用其它线程执行任务
        );
        return pool;
    }
}
