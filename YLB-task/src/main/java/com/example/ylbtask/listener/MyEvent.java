package com.example.ylbtask.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

//自定义事件
public class MyEvent extends ApplicationEvent {

//    事件构造器用父类实现
    public MyEvent(Object source) {
        super(source);
    }
}
