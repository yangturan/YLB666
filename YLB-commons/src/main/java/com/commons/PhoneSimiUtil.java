package com.commons;

public class PhoneSimiUtil {

//    隐式显示电话号码方法
    public static String phoneSimi(String phone){
        String result="***********";
        if(phone!=null&&phone.trim().length()==11){
            result=phone.trim().substring(0,3)+"******"+phone.trim().substring(9,11);
        }
        return result;
    }

//    电话号码格式验证方法
    public static boolean phoneTure(String phone){
        String regex = "^1[3-9]\\d{9}$";
        if(phone.matches(regex)){
            return true;
        }else{
            return false;
        }
    }
}
