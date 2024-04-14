package com.commons;

//分页查询时参数验证的工具类
public class PageValidationUtil {

    //页面序号参数验证
    public static int pageNoValidation(Integer pageNo){
        int temp=pageNo;
        if(pageNo==null||pageNo<1){
            temp=1;
        }
        return temp;
    }

    //每页个数参数验证
    public static int pageSizeValidation(Integer pageSize){
        int temp=pageSize;
        if(pageSize==null||pageSize<1){
            temp=1;
        }
        return temp;
    }
}
