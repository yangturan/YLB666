package com.example.ylbtask.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventApplicationListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("触发了监听事件哦");
        System.out.println(event.getSource());
        System.out.println("开始执行定时任务");
    }
}
