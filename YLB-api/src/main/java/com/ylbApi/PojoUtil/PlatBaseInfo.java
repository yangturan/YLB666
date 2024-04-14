package com.ylbApi.PojoUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

//基本信息汇总类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatBaseInfo implements Serializable {

//    平均年化利率
    private BigDecimal allYearUp;

//    总成交金额
    private BigDecimal allMoney;

//    用户数
    private Integer userCount;
}
