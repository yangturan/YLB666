package com.ylbdataService.mapper;

import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.UUser;

import java.util.Date;
import java.util.List;

/**
* @author 32966
* @description 针对表【u_user(用户表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:22
* @Entity com.ylbdataService.Pojo.UUser
*/
public interface UUserMapper {

//    获取用户总数
    Integer getUserCount();

//    验证注册的手机号码是否存在
    List<UUser> phoneExits(String phone);

    int registry(String phone, String password);

    List<UUser> login(String phone, String loginPassword);

    Integer getIdByPhone(String phone);

    int headerImageUp(String path,String phone);

    String getHeaderImage(String phone);

    Date getLastLogin(String phone);

    int makeLastTime(String phone);

    String getPhoneById(Integer integer);
}




