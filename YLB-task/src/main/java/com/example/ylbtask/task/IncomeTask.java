package com.example.ylbtask.task;

import com.ylbApi.Pojo.BIncomeRecord;
import com.ylbApi.Service.TaskService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeTask {

    @DubboReference(interfaceClass = TaskService.class,version = "1.0.0")
    private TaskService taskService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void IncomeOverTimeGiveMoney(){
        //检查并返还到期的订单，每分钟执行一次
        System.out.println("我执行了");
        taskService.IncomeOverTimeGiveMoney();
    }
}
