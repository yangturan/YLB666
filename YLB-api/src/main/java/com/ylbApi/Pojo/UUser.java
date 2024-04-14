package com.ylbApi.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;

/**
* 用户表
* @TableName u_user
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UUser implements Serializable {

    /**
    * 用户ID，主键
    */
    private Integer id;

    /**
     * 注册用验证码
     */
    private String yzm;
    /**
    * 注册手机号码
    */

    private String phone;
    /**
    * 登录密码，密码长度最大16位
    */
    private String loginPassword;
    /**
    * 用户姓名
    */
    private String name;
    /**
    * 用户身份证号码
    */
    private String idCard;
    /**
    * 注册时间
    */
    private Date addTime;
    /**
    * 最近登录时间
    */
    private Date lastLoginTime;
    /**
    * 用户头像文件路径
    */
    private String headerImage;

    public UUser(String phone,String loginPassword){
        this.phone=phone;
        this.loginPassword=loginPassword;
    }
}
