package com.example.ylbweb.runner;

import com.commons.PhoneSimiUtil;
import com.commons.contants.RedisKey;
import com.ylbApi.PojoUtil.InventRank;
import com.ylbApi.Service.InventService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
//在springboot一启动时就执行某些方法
public class StartRun implements ApplicationRunner {
    @DubboReference(interfaceClass = InventService.class, version = "1.0.0")
    private InventService inventService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${ylb.expire-time2}")
    private Integer expire2;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //    排行榜信息预热
            System.out.println("启动时缓存预热");
            if(!stringRedisTemplate.opsForZSet().range(RedisKey.INVENT_RANK_KEY2,0,1).isEmpty()){
                stringRedisTemplate.delete(RedisKey.INVENT_RANK_KEY2);
            }
            List<InventRank> rank = inventService.getRank();
            for (int i = 0; i < rank.size(); i++) {
                rank.get(i).setPhone(PhoneSimiUtil.phoneSimi(rank.get(i).getPhone()));
            }
            ZSetOperations<String, String> stringStringZSetOperations = stringRedisTemplate.opsForZSet();
            for (int i = 0; i < rank.size(); i++) {
                stringStringZSetOperations.add(RedisKey.INVENT_RANK_KEY2, rank.get(i).getPhone(), rank.get(i).getMoney());
            }
///           设置不同的缓存时间预防缓存雪崩
            stringRedisTemplate.expire(RedisKey.INVENT_RANK_KEY2, expire2, TimeUnit.SECONDS);
        }
    }
