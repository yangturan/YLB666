package com.example.ylbweb.Controller;

import com.commons.PhoneSimiUtil;
import com.commons.contants.RedisKey;
import com.example.ylbweb.BaseController.BaseController;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.PojoUtil.InventRank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//排行榜控制器
@CrossOrigin
@RestController
@RequestMapping("/v1")
@Tag(name = "投资类",description = "用于获取投资相关的信息数据")
public class InventController extends BaseController {

    @Value("${ylb.expire-time2}")
    private Integer expire2;

    @Operation(summary = "用redis获取投资排行榜前三信息", description = "redis没有数据的情况下需要从数据库查找")
    //获取投资排行榜用户信息
    @GetMapping("/invest/rank")
    public MyResult getMoneyRanking() {
        List<InventRank> rank = inventService.getRank();
        if (stringRedisTemplate.opsForZSet().reverseRange(RedisKey.INVENT_RANK_KEY2, 0, 2).isEmpty()) {
            System.out.println("没有进入redis");
            for (int i = 0; i < rank.size(); i++) {
                rank.get(i).setPhone(PhoneSimiUtil.phoneSimi(rank.get(i).getPhone()));
            }
            ZSetOperations<String, String> stringStringZSetOperations = stringRedisTemplate.opsForZSet();
            for (int i = 0; i < rank.size(); i++) {
                stringStringZSetOperations.add(RedisKey.INVENT_RANK_KEY2,rank.get(i).getPhone(),rank.get(i).getMoney());
            }
//            设置不同的缓存时间预防缓存雪崩
            stringRedisTemplate.expire(RedisKey.INVENT_RANK_KEY2,expire2, TimeUnit.SECONDS);
            MyResult result = MyResult.ok();
            result.setData(rank);
            return result;
        } else {
            System.out.println("没有进入数据库");
            Set<ZSetOperations.TypedTuple<String>> zsets = stringRedisTemplate.boundZSetOps(RedisKey.INVENT_RANK_KEY2).reverseRangeWithScores(0, 2);
            zsets.forEach(zset -> {
                rank.add(new InventRank(PhoneSimiUtil.phoneSimi(zset.getValue()), zset.getScore()));
            });
            MyResult result = MyResult.ok();
            result.setData(rank);
            return result;
        }
    }
}
