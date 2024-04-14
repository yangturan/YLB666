package com.ylbApi.Enum;

//此类的text在MyResult类中被赋值给msg
public enum ResultEnum {
    //错误状态码0
    ERR(0,"请求失败"),
//    成功状态码1000
    SUCCESS(1000,"请求成功"),
    ERR_NULL(1100,"没有任何数据符合要求"),
    ERR_PARAM(1200,"请求参数违规"),
    PHONE_FALSE(1300,"电话号码不规范"),
    PHONE_EXITS(1400,"电话号码已存在"),
    PASSWORD_FALSE(1500,"密码强度不佳"),
    CODE_FALSE(1600,"验证码错误"),
    LOGIN_ERR(1700,"用户名或密码不正确"),
    REDIS_DELETE_FAIL(1800,"后台登录信息删除失败"),
    FILE_NULL(1900,"上传文件为空"),
    IMAGE_FALSE(2000,"仅支持jpg，webp，png"),
    NO_DO(9999,"对于此错误，你无需任何操作"),
    IMAGE_NULL(2100,"头像加载错误"),
    DATE_FALSE(2200,"时间处理错误"),
    TOKEN_NULL(3000,"跳转到首页"),
    TOUZI_FALSE(2300,"投资事务出错，请重试")

    ;

    private int code;
    private String text;

    ResultEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

}
