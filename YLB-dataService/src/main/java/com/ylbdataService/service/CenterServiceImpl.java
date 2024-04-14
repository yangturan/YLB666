package com.ylbdataService.service;

import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BIncomeRecord;
import com.ylbApi.Pojo.BRechargeRecord;
import com.ylbApi.Pojo.UFinanceAccount;
import com.ylbApi.Service.CenterService;
import com.ylbdataService.mapper.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = CenterService.class,version = "1.0.0")
public class CenterServiceImpl implements CenterService {

    @Autowired
    private UUserMapper uUserMapper;

    @Autowired
    private BBidInfoMapper bBidInfoMapper;

    @Autowired
    private BIncomeRecordMapper bIncomeRecordMapper;

    @Autowired
    private BRechargeRecordMapper bRechargeRecordMapper;

    @Autowired
    private BLoanInfoMapper bLoanInfoMapper;

    @Autowired
    private UFinanceAccountMapper uFinanceAccountMapper;
    //投资
    @Override
    public List<BBidInfo> getBid(Integer uid) {
        List<BBidInfo> bids = bBidInfoMapper.getBid(uid);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BBidInfo bid : bids) {
            String name=bBidInfoMapper.getLoanNameById(bid.getLoanId());
            bid.setName(name);
            String format = sdf.format(bid.getBidTime());
            bid.setTime(format);
        }
        return bids;
    }

    //收益
    @Override
    public List<BIncomeRecord> getIncome(Integer uid) {
        List<BIncomeRecord> incomes = bIncomeRecordMapper.getIncome(uid);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BIncomeRecord income : incomes) {
            String name=bBidInfoMapper.getLoanNameById(income.getLoanId());
            income.setName(name);
            String format = sdf.format(income.getIncomeDate());
            income.setTime(format);
        }
        return incomes;
    }

    //充值
    @Override
    public List<BRechargeRecord> getRecharge(Integer uid) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<BRechargeRecord> recharges=bRechargeRecordMapper.getRecharge(uid);
        for (BRechargeRecord recharge : recharges) {
            String format = sdf.format(recharge.getRechargeTime());
            recharge.setTime(format);
        }
        return recharges;
    }

    //通过电话获取用户id方法
    @Override
    public Integer getUidByPhone(String phone) {
        return uUserMapper.getIdByPhone(phone);
    }

    @Override
    public BigDecimal getAccountMoney(Integer uid) {
        return uFinanceAccountMapper.getAccountMoney(uid);
    }

    @Override
    public String getUserBeforeDate(String phone) {
        Date d1=uUserMapper.getLastLogin(phone);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(d1);
        return format;
    }
}
