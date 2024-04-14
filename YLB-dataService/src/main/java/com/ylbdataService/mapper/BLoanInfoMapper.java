package com.ylbdataService.mapper;


import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BLoanInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 32966
* @description 针对表【b_loan_info(产品信息表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:21
* @Entity com.ylbdataService.Pojo.BLoanInfo
*/
public interface BLoanInfoMapper{


    String getProductName(Integer id);

    //    获取平均年化利率方法
    BigDecimal getYearUp();

//    获取分页商品方法
    List<BLoanInfo> getLimitProduct(Integer pType, int index, Integer pageSize);

//    查询某一类商品总数
    Integer getProductTypeCount(Integer pType);

    List<BBidInfo> getProductTraffic(Integer productId);

    int setLeftMoney(@Param("productId") Integer productId, @Param("money") BigDecimal money);
}




