package com.ylbApi.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 投资记录表
* @TableName b_bid_info
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BBidInfo implements Serializable {

    /**
     * 投标记录ID
     */
    private Integer id;
    /**
     * 投资人电话
     */
    private String phone;
    /**
     * 产品ID
     */
    private Integer loanId;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 投标金额
     */
    private BigDecimal bidMoney;
    /**
     * 投标时间
     */
    private Date bidTime;
    private String time;
    /**
     * 投标状态
     */
    private Integer bidStatus;

}
