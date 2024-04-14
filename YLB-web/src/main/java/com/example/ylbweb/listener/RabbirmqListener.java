package com.example.ylbweb.listener;

import com.commons.contants.MQkey;
import com.ylbApi.Pojo.UUser;
import com.ylbApi.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbirmqListener {

    @Resource
    private UserService userService;


    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = MQkey.DENGLU_QUEUE,durable = "false"),
            exchange = @Exchange(name = MQkey.DENGLU_EXCHANGE,type = ExchangeTypes.DIRECT,durable = "false"),
            key = {MQkey.DENGLU_KEY}
    ))
    public void dengluListener(UUser uUser){
        System.out.println("开始执行登录rabbitmq的事务");
        int i1 = userService.makeLastDate(uUser.getPhone());
        System.out.println("执行完成，返回值是"+i1);
    }
}
