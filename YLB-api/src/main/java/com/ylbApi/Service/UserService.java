package com.ylbApi.Service;

public interface UserService {
    boolean phoneExits(String phone);

    int registry(String phone, String password);

    int login(String phone, String loginPassword);

    String getCode(String phone) throws Exception;

    //给对应用户上传头像路径到数据库
    int headerImageUp(String path,String phone);

    //根据电话号码查询用户头像路径
    String getHeaderImage(String phone);

    //生成用户最近登录日期
    int makeLastDate(String phone);
}
