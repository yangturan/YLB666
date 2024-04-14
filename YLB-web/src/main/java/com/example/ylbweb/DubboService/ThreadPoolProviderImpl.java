package com.example.ylbweb.DubboService;

import com.ylbApi.Service.ThreadPoolProvider;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@DubboService(interfaceClass = ThreadPoolProvider.class,version = "1.0.0")
public class ThreadPoolProviderImpl implements ThreadPoolProvider {

    @Autowired
    protected ThreadPoolExecutor threadPoolExecutor;


    @Override
    public ThreadPoolExecutor getPool() {
        return threadPoolExecutor;
    }


}
