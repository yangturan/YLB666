package com.ylbApi.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 收益记录表
* @TableName b_income_record
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BIncomeRecord implements Serializable {

    /**
    *
    */
    private Integer id;
    /**
    * 用户ID
    */
    private Integer uid;
    /**
    * 产品ID
    */
    private Integer loanId;
    /**
     * 产品名称
     */
    private String name;
    /**
    * 投标记录ID
    */
    private Integer bidId;
    /**
    * 投资金额
    */
    private BigDecimal bidMoney;
    /**
    * 收益时间
    */
    private Date incomeDate;
    private String time;
    /**
    * 收益金额
    */
    private BigDecimal incomeMoney;
    /**
    * 收益状态（0未返，1已返）
    */
    private Integer incomeStatus;

}
