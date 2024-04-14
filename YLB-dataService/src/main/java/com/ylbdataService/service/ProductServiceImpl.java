package com.ylbdataService.service;

import com.commons.PageValidationUtil;
import com.commons.PhoneSimiUtil;
import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BLoanInfo;
import com.ylbApi.Pojo.UFinanceAccount;
import com.ylbApi.PojoUtil.IndexPageLimit;
import com.ylbApi.PojoUtil.MybatisKeyPropertyUtil;
import com.ylbApi.Service.ProductService;
import com.ylbdataService.mapper.BBidInfoMapper;
import com.ylbdataService.mapper.BIncomeRecordMapper;
import com.ylbdataService.mapper.BLoanInfoMapper;
import com.ylbdataService.mapper.UFinanceAccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = ProductService.class,version = "1.0.0")
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private BLoanInfoMapper bLoanInfoMapper;

    @Autowired
    private BBidInfoMapper bBidInfoMapper;

    @Autowired
    private UFinanceAccountMapper uFinanceAccountMapper;

    @Autowired
    private BIncomeRecordMapper bIncomeRecordMapper;

//    获取分页商品方法
    @Override
    public List<BLoanInfo> getLimitProduct(Integer pType, Integer pageNo, Integer pageSize) {
        //先做数据验证，用到了commons模块的分页验证工具类
        if(pType==0||pType==1||pType==2){
            pageNo= PageValidationUtil.pageNoValidation(pageNo);
            pageSize= PageValidationUtil.pageSizeValidation(pageSize);
            //定义limit查询开始的下标
            int index=(pageNo-1)*pageSize;
            List<BLoanInfo> bLoanInfos=bLoanInfoMapper.getLimitProduct(pType,index,pageSize);
            return bLoanInfos;
        }
        return null;
    }

//    首页直接查询出三种不同类型商品分页结果的方法
    public IndexPageLimit getAllLimitProduct(){
        IndexPageLimit allLimit=new IndexPageLimit();
        allLimit.setXinshou(getLimitProduct(0,1,1));
        allLimit.setYouxuan(getLimitProduct(1,1,3));
        allLimit.setSanbiao(getLimitProduct(2,1,3));
        return allLimit;
    }

//    查询某一类型商品的总数
    @Override
    public Integer getProductTypeCount(Integer pType) {
        Integer count=bLoanInfoMapper.getProductTypeCount(pType);
        return count;
    }

//    根据商品id查询商品交易记录方法
    @Override
    public List<BBidInfo> getProductTraffic(Integer productId) {
        List<BBidInfo> bBidInfos=bLoanInfoMapper.getProductTraffic(productId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BBidInfo bBidInfo : bBidInfos) {
            String format = sdf.format(bBidInfo.getBidTime());
            bBidInfo.setTime(format);
            bBidInfo.setPhone(PhoneSimiUtil.phoneSimi(bBidInfo.getPhone()));
        }
        return bBidInfos;
    }

//    根据商品id获取商品名称
    @Override
    public String getProductName(Integer id) {
        String name=bLoanInfoMapper.getProductName(id);
        return name;
    }

    @Override
    public int maketouzi(Integer productId, Integer uidByPhone, BigDecimal money, MybatisKeyPropertyUtil mybatisKeyPropertyUtil) {
        return bBidInfoMapper.makeTouzi(productId,uidByPhone,money,mybatisKeyPropertyUtil);
    }

    @Override
    public int makeshouyi(Integer productId, Integer uidByPhone, BigDecimal money, BigDecimal incomeMoney, Integer cycle,Integer bidId) {
        if(productId.equals(1310699)){
            return bIncomeRecordMapper.makeXinshouShouyi(productId,uidByPhone,money,incomeMoney,cycle,bidId);
        }
        return bIncomeRecordMapper.makeshouyi(productId,uidByPhone,money,incomeMoney,cycle,bidId);
    }

    @Override
    public int setLeftMoney(Integer productId, BigDecimal money) {
        return bLoanInfoMapper.setLeftMoney(productId,money);
    }

    @Override
    public int setAccountMoney(Integer uidByPhone, BigDecimal money) {
        return uFinanceAccountMapper.setAccountMoney(uidByPhone,money);
    }
}
