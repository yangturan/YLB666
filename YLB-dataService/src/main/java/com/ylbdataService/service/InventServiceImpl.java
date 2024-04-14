package com.ylbdataService.service;

import com.ylbApi.PojoUtil.InventRank;
import com.ylbApi.PojoUtil.InventRankByUid;
import com.ylbApi.Service.InventService;
import com.ylbdataService.mapper.BBidInfoMapper;
import com.ylbdataService.mapper.UUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = InventService.class,version = "1.0.0")
@Transactional
public class InventServiceImpl implements InventService {

    @Autowired
    private BBidInfoMapper bBidInfoMapper;

    @Autowired
    private UUserMapper uUserMapper;
    @Override
    public List<InventRank> getRank() {
        List<InventRankByUid> list = bBidInfoMapper.getRankPhone();
        ArrayList<InventRank> phones=new ArrayList<>();
        for (InventRankByUid inventRankByUid : list) {
            phones.add(new InventRank(uUserMapper.getPhoneById(inventRankByUid.getUid()),inventRankByUid.getMoney()));
        }
        return phones;
    }
}
