package com.ylbApi.PojoUtil;

import com.ylbApi.Pojo.BLoanInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//首页直接查询三种商品分页的快捷方法辅助类
public class IndexPageLimit implements Serializable {
    private List<BLoanInfo> xinshou=new ArrayList<BLoanInfo>();
    private List<BLoanInfo> youxuan=new ArrayList<BLoanInfo>();
    private List<BLoanInfo> sanbiao=new ArrayList<BLoanInfo>();
}
