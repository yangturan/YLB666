package com.example.ylbtask.config;

import com.ylbApi.Service.ThreadPoolProvider;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

//用来实验将线程池交给duboo共享给所有微服务
@Configuration
public class ScheduledThreadConfig implements SchedulingConfigurer {

    @DubboReference(interfaceClass = ThreadPoolProvider.class,version = "1.0.0")
    private ThreadPoolProvider poolProvider;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(3));
    }
}
