package com.ylbApi.PojoUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class MybatisKeyPropertyUtil implements Serializable {
    public MybatisKeyPropertyUtil(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        System.out.println("测试，我被赋值了"+id);
        this.id = id;
    }

    private Integer id;
}
