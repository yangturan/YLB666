package com.ylbdataService.mapper;



import com.ylbApi.Pojo.BIncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author 32966
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:21
* @Entity com.ylbdataService.Pojo.UFinanceAccount
 * 账户资金表
*/
public interface UFinanceAccountMapper{

    BigDecimal getAccountMoney(Integer uid);

    int setAccountMoney(@Param("uid") Integer uidByPhone, @Param("money") BigDecimal money);

    int giveMoney(BIncomeRecord income);
}




