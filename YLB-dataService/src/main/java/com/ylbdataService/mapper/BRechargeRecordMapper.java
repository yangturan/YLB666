package com.ylbdataService.mapper;

import com.ylbApi.Pojo.BRechargeRecord;

import java.util.List;

/**
* @author 32966
* @description 针对表【b_recharge_record(充值记录表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:21
* @Entity com.ylbdataService.Pojo.BRechargeRecord
 * 充值记录表
 *
*/
public interface BRechargeRecordMapper{

    List<BRechargeRecord> getRecharge(Integer uid);
}




