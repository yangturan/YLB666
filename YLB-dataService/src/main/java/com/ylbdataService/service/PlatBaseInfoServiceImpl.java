package com.ylbdataService.service;

import com.ylbApi.PojoUtil.IndexPageLimit;
import com.ylbApi.PojoUtil.PlatBaseInfo;
import com.ylbApi.Service.PlatBaseInfoService;
import com.ylbdataService.mapper.BBidInfoMapper;
import com.ylbdataService.mapper.BLoanInfoMapper;
import com.ylbdataService.mapper.UUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

//汇总信息服务实现类
//Dubbo服务接口声明注解，声明该类是一个对其他Duboo项目提供服务的类，参数声明了接口类和版本号
//这个注解在Dubbo中也能起到类似Service注解的作用
@DubboService(interfaceClass = PlatBaseInfoService.class,version = "1.0.0")
@Transactional
public class PlatBaseInfoServiceImpl implements PlatBaseInfoService {

//    提供平均年化收益的Dao接口
    @Autowired
    private BLoanInfoMapper bLoanInfoMapper;

//    提供用户总数的接口
    @Autowired
    private UUserMapper uUserMapper;

//    提供交易总额的接口
    @Autowired
    private BBidInfoMapper bBidInfoMapper;

    @Override
    public PlatBaseInfo getPlatBaseInfo() {
        PlatBaseInfo platBaseInfo=new PlatBaseInfo();
        platBaseInfo.setAllMoney(bBidInfoMapper.getAllMoney());
        BigDecimal yearUp = bLoanInfoMapper.getYearUp();
        //setScale用于设置保留小数点的位数，UP代表向上取整
        BigDecimal bigDecimal = yearUp.setScale(2, RoundingMode.UP);
        platBaseInfo.setAllYearUp(bigDecimal);
        platBaseInfo.setUserCount(uUserMapper.getUserCount());
        return platBaseInfo;
    }

}
