package com.ylbApi.Service;

import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BLoanInfo;
import com.ylbApi.PojoUtil.IndexPageLimit;
import com.ylbApi.PojoUtil.MybatisKeyPropertyUtil;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

//    获取分页商品信息方法，参数分别是商品类型，商品下标，商品个数
    List<BLoanInfo> getLimitProduct(Integer pType,Integer pageNo,Integer pageSize);

//   获取首页全部分页信息方法
    IndexPageLimit getAllLimitProduct();

//    查询某一类商品总数的方法
    Integer getProductTypeCount(Integer pType);

//    根据商品id查询商品交易记录方法
    List<BBidInfo> getProductTraffic(Integer productId);

//    根据商品id查询商品名称方法
    String getProductName(Integer id);

//    生成投资单方法
    int maketouzi(Integer productId, Integer uidByPhone, BigDecimal money, MybatisKeyPropertyUtil mybatisKeyPropertyUtil);

//    生成收益单方法
    int makeshouyi(Integer productId, Integer uidByPhone, BigDecimal money, BigDecimal incomeMoney, Integer cycle,Integer bidId);

//    改变商品可投资金额方法
    int setLeftMoney(Integer productId, BigDecimal money);

//    改变用户剩余金额方法
    int setAccountMoney(Integer uidByPhone, BigDecimal money);
}
