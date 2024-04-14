package com.ylbdataService.mapper;


import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.PojoUtil.InventRank;
import com.ylbApi.PojoUtil.InventRankByUid;
import com.ylbApi.PojoUtil.MybatisKeyPropertyUtil;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 32966
* @description 针对表【b_bid_info(投资记录表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:21
* @Entity com.ylbdataService.Pojo.BBidInfo
 * 投资记录表
*/
public interface BBidInfoMapper{

//    计算总成交额方法
    BigDecimal getAllMoney();

    List<BBidInfo> getBid(Integer uid);

    String getLoanNameById(Integer loanId);

    //用户投资时生成收益单的方法
    int makeTouzi(@Param("loanId") Integer productId, @Param("uid") Integer uidByPhone, @Param("money") BigDecimal money, @Param("key")MybatisKeyPropertyUtil mybatisKeyPropertyUtil);

    List<InventRankByUid> getRankPhone();
}




