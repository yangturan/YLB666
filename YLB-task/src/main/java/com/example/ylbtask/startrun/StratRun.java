package com.example.ylbtask.startrun;

import com.example.ylbtask.listener.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
//springboot一启动就推送我的自定义监听事件
public class StratRun implements ApplicationRunner {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("进入了初始化方法");
        applicationEventPublisher.publishEvent(new MyEvent(this));
    }
}
