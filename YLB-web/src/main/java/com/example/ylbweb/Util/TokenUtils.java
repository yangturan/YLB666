package com.example.ylbweb.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.commons.Exception.BusinessException;
import com.commons.contants.RedisKey;
import com.ylbApi.Pojo.UUser;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {

    //注入redis模板
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //注入配置文件中的warehouse.expire-time属性 -- token的过期时间
    @Value("${ylb.expire-time}")
    private int expireTime;

    /**
     * 常量:
     */
    //token中存放用户对应的电话
    private static final String CLAIM_PHONE = "CLAIM_PHONE";
    //token中存放用户对应的密码
    private static final String CLAIM_PASSWORD = "CLAIM_PASSWORD";

    private String sign(UUser currentUser, String securityKey){
        String token = JWT.create()
                .withClaim(CLAIM_PHONE, currentUser.getPhone())
                .withClaim(CLAIM_PASSWORD, currentUser.getLoginPassword())
                .withIssuedAt(new Date())//发行时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime *1000))//有效时间
                .sign(Algorithm.HMAC256(securityKey));
        return token;
    }

    /**
     * 将当前用户信息以用户密码为密钥生成token的方法
     */
    public String loginSign(UUser currentUser, String password){
        //生成token
        String token = sign(currentUser, password);
        //将token保存到redis中,并设置token在redis中的过期时间
        stringRedisTemplate.opsForValue().set(currentUser.getPhone()+RedisKey.USER_TOKEN, token, expireTime *2, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 从客户端归还的token中获取用户信息的方法
     */
    public UUser getCurrentUser(String token) {
        if(StringUtils.isEmpty(token)){
            throw new BusinessException("令牌为空，请登录！");
        }
        //对token进行解码,获取解码后的token
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new BusinessException("令牌格式错误，请登录！");
        }
        //从解码后的token中获取用户信息并封装到CurrentUser对象中返回
        String phone = decodedJWT.getClaim(CLAIM_PHONE).asString();//用户账号
        String password = decodedJWT.getClaim(CLAIM_PASSWORD).asString();//用户姓名
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new BusinessException("令牌缺失用户信息，请登录！");
        }
        return new UUser(phone,password);
    }

}
