package com.ylbApi.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.math.BigDecimal;

/**
* 用户财务资金账户表
* @TableName u_finance_account
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UFinanceAccount implements Serializable {

    /**
    *
    */
    private Integer id;
    /**
    * 用户ID
    */
    private Integer uid;
    /**
    * 用户可用资金
    */
    private BigDecimal availableMoney;

}
