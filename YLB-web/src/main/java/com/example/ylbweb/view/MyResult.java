package com.example.ylbweb.view;

import com.ylbApi.Enum.ResultEnum;
import com.ylbApi.PojoUtil.Page;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//  特定返回结果类
public class MyResult {
    private Integer code;
    private String msg;
    private Object data;
    private Page page;
    private String token;

//    利用枚举工具类返回文本信息和状态码
    public static MyResult ok(){
        MyResult myResult=new MyResult();
        myResult.getEnum(ResultEnum.SUCCESS);
        return myResult;
    }

    public static MyResult err(ResultEnum resultEnum){
        MyResult myResult=new MyResult();
        myResult.getEnum(resultEnum);
        return myResult;
    }

    public void getEnum(ResultEnum resultEnum){
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getText();
    }

}
