package com.ylbdataService.mapper;


import com.ylbApi.Pojo.BIncomeRecord;
import org.apache.ibatis.annotations.Param;


import java.math.BigDecimal;
import java.util.List;

/**
* @author 32966
* @description 针对表【b_income_record(收益记录表)】的数据库操作Mapper
* @createDate 2024-01-09 13:56:21
* @Entity com.ylbdataService.Pojo.BIncomeRecord
 * 收益记录表
*/
public interface BIncomeRecordMapper{

    List<BIncomeRecord> getIncome(Integer uid);

    int makeXinshouShouyi(@Param("loanId") Integer productId, @Param("uid")Integer uidByPhone, @Param("money")BigDecimal money, @Param("incomeMoney")BigDecimal incomeMoney, @Param("cycle")Integer cycle, @Param("bidId")Integer bidId);

    int makeshouyi(@Param("loanId") Integer productId, @Param("uid")Integer uidByPhone, @Param("money")BigDecimal money, @Param("incomeMoney")BigDecimal incomeMoney, @Param("cycle")Integer cycle,@Param("bidId")Integer bidId);

    List<BIncomeRecord> getIncomeOverTime();

    int updateStatus(BIncomeRecord income);
}




