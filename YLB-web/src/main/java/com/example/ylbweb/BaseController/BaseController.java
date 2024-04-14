package com.example.ylbweb.BaseController;

import com.example.ylbweb.Interceptor.TokenInterceptor;
import com.example.ylbweb.Util.TokenUtils;
import com.ylbApi.Service.*;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

//公共的父类，获取Dubbo的接口给Controller使用,只需设置Controller类继承该类即可
public class BaseController implements WebMvcConfigurer {

// redis模板类
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

//    线程池
    @Autowired
    protected ThreadPoolExecutor threadPoolExecutor;

//    Dubbo服务获取注解，获取对应的@DubboService的服务提供者类
    @DubboReference(interfaceClass = PlatBaseInfoService.class,version = "1.0.0")
    protected PlatBaseInfoService platBaseInfoService;

//    商品服务获取
    @DubboReference(interfaceClass = ProductService.class,version = "1.0.0")
    protected ProductService productService;

//    用户服务
    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    protected UserService userService;

    @Resource
    protected TokenUtils tokenUtils;

    @DubboReference(interfaceClass = CenterService.class,version = "1.0.0")
    protected CenterService centerService;

    @DubboReference(interfaceClass = InventService.class,version = "1.0.0")
    protected InventService inventService;
}
