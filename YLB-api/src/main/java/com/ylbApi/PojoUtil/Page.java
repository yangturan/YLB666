package com.ylbApi.PojoUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Serializable {
    private Integer pageSize;
    private Integer count;
    private Integer pageNo;
    private Integer pageCount;

    public Page(Integer pageSize, Integer count, Integer pageNo) {
        this.pageSize = pageSize;
        this.count = count;
        this.pageNo = pageNo;
        if(count%pageSize==0){
            this.pageCount=count/pageSize;
        }else{
            this.pageCount=(count/pageSize)+1;
        }
    }
}
