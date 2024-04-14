package com.ylbdataService.service;

import com.ylbApi.Pojo.BIncomeRecord;
import com.ylbApi.Service.TaskService;
import com.ylbdataService.mapper.BIncomeRecordMapper;
import com.ylbdataService.mapper.UFinanceAccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = TaskService.class,version = "1.0.0")
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private BIncomeRecordMapper bIncomeRecordMapper;

    @Autowired
    private UFinanceAccountMapper uFinanceAccountMapper;

    @Override
    public void IncomeOverTimeGiveMoney() {
        //查询所有需要返还的订单
        List<BIncomeRecord> incomes = bIncomeRecordMapper.getIncomeOverTime();
        if (incomes.size()>0){
            for (BIncomeRecord income : incomes) {
                //修改利息单的状态
                int i=bIncomeRecordMapper.updateStatus(income);
                int i2= uFinanceAccountMapper.giveMoney(income);
                //为了transactional注解抛出异常，让所有事务回滚
                if(i==0||i2==0) {throw new RuntimeException();}
            }
        }else{
            System.out.println("没有需要执行的事务");
        }
    }
}
