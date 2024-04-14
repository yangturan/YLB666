package com.ylbApi.Service;

import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BIncomeRecord;
import com.ylbApi.Pojo.BRechargeRecord;

import java.math.BigDecimal;
import java.util.List;

public interface CenterService {
    //获取最近投资记录方法
    List<BBidInfo> getBid(Integer uid);

    //获取最近收益记录方法
    List<BIncomeRecord> getIncome(Integer uid);

//    获取最近充值记录方法
    List<BRechargeRecord> getRecharge(Integer uid);

//    通过电话号码查询用户id方法
    Integer getUidByPhone(String phone);

//    通过电话号码查询用户账户余额方法
    BigDecimal getAccountMoney(Integer uid);

//    查询用户最近登录的时间
    String getUserBeforeDate(String phone);
}
