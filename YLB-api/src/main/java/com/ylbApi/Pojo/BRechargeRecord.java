package com.ylbApi.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

/**
* 充值记录表
* @TableName b_recharge_record
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BRechargeRecord implements Serializable {

    /**
    *充值单id
    */
    private Integer id;
    /**
    * 用户id
    */
    private Integer uid;
    /**
    * 充值订单号
    */
    private String rechargeNo;
    /**
    * 充值订单状态（0充值中，1充值成功，2充值失败）
    */
    private Integer rechargeStatus;
    /**
    * 充值金额
    */
    private BigDecimal rechargeMoney;
    /**
    * 充值时间
    */
    private Date rechargeTime;
    //方便前后端传递的字符串型时间属性
    private String time;
    /**
    * 充值描述
    */
    private String rechargeDesc;
    /**
    *
    */
    private String channel;
}
