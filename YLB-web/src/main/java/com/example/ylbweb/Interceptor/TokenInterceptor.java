package com.example.ylbweb.Interceptor;

import com.alibaba.fastjson.JSON;
import com.commons.contants.RedisKey;
import com.example.ylbweb.Util.TokenUtils;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.Enum.ResultEnum;
import com.ylbApi.Pojo.UUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.time.Duration;

@Component
public class TokenInterceptor implements HandlerInterceptor {

//由于过滤器的加载时间在spring的上下文加载之前，因此我们需要再配置类中先用
//    bean注解注入当前拦截器才能在这个拦截器中注入redis
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${ylb.expire-time}")
    private Integer expireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行跨域请求
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }
        if(request.getRequestURI().contains("center")&&!request.getRequestURI().contains("center/image")){
            String mytoken = request.getHeader("Thisistoken");
            String phone = request.getHeader("Thephone");
            if(mytoken!=null&&phone!=null) {
                if (stringRedisTemplate.opsForValue().get(phone + RedisKey.USER_TOKEN)!=null&&stringRedisTemplate.opsForValue().get(phone + RedisKey.USER_TOKEN).equals(mytoken)) {
                    //更新用户token时间
                    stringRedisTemplate.expire(phone+RedisKey.USER_TOKEN, Duration.ofSeconds(expireTime));
                    return true;
                } else {
                    MyResult ok = MyResult.err(ResultEnum.TOKEN_NULL);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    String s = JSON.toJSONString(ok);
                    writer.print(s);
                    writer.close();
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}
