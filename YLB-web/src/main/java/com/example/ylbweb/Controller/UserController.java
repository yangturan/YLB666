package com.example.ylbweb.Controller;

import com.commons.PasswordUtil;
import com.commons.PhoneSimiUtil;
import com.commons.contants.MQkey;
import com.commons.contants.RedisKey;
import com.example.ylbweb.BaseController.BaseController;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.Enum.ResultEnum;
import com.ylbApi.Pojo.UUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1")
@CrossOrigin
@Tag(name = "用户相关类",description = "定义了用户注册登录等方法")
public class UserController extends BaseController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/user/registry")
    @Operation(summary = "用户注册功能",description = "包括手机号验证格式以及是否注册过")
    @Parameters({
            @Parameter(name = "phone",description = "手机号"),
            @Parameter(name = "password",description = "密码")
    })
    public MyResult UserRegistry(@RequestBody UUser uUser){
        if(PhoneSimiUtil.phoneTure(uUser.getPhone())){
            if(PasswordUtil.passwordTrue(uUser.getLoginPassword())) {
                boolean b = userService.phoneExits(uUser.getPhone());
                if (b) {
                    String phone = uUser.getPhone();
                    String code = stringRedisTemplate.opsForValue().get(phone + RedisKey.CODE_NUMBER);
                    if(!code.equals(uUser.getYzm())){
                        return MyResult.err(ResultEnum.CODE_FALSE);
                    }
                    int i=userService.registry(uUser.getPhone(),uUser.getLoginPassword());
                    if(i>0){
                        return MyResult.ok();
                    }else{
                        return MyResult.err(ResultEnum.ERR);
                    }
                } else {
                    return MyResult.err(ResultEnum.PHONE_EXITS);
                }
            }else{
                return MyResult.err(ResultEnum.PASSWORD_FALSE);
            }
        }else{
            return MyResult.err(ResultEnum.PHONE_FALSE);
        }
    }

    @PostMapping("/user/login")
    @Operation(summary = "用户登录功能",description = "检测用户是否存在，密码是否正确并登录")
    @Parameters({
            @Parameter(name = "phone",description = "手机号"),
            @Parameter(name = "password",description = "密码")
    })
    public MyResult Login(@RequestBody UUser uUser, HttpServletResponse response){
        if(PhoneSimiUtil.phoneTure(uUser.getPhone())){
            if(PasswordUtil.passwordTrue(uUser.getLoginPassword())) {
                    int i=userService.login(uUser.getPhone(),uUser.getLoginPassword());
                            if (i > 0) {
//                                线程池实现异步操作
//                                threadPoolExecutor.submit(()->{
//                                    try {
//                                        //在异步线程中添加最近登录时间给数据库
//                                        int i1 = userService.makeLastDate(uUser.getPhone());
//                                    }catch (Exception e){
//                                        System.out.println("你的登录方法异步线程出错了");
//                                        e.printStackTrace();
//                                    }
//                                });
//                                rabbitmq实现异步操作
                                rabbitTemplate.convertAndSend(MQkey.DENGLU_EXCHANGE,MQkey.DENGLU_KEY,uUser);
                                //需要生成并携带token传给前端
                                MyResult ok = MyResult.ok();
                                UUser u1 = new UUser(uUser.getPhone(), uUser.getLoginPassword());
//                              //这一步生成了token并设置了过期时间
                                tokenUtils.loginSign(u1, uUser.getLoginPassword());
                                String token = stringRedisTemplate.opsForValue().get(u1.getPhone() + RedisKey.USER_TOKEN);
                                ok.setToken(token);
                                ok.setData(u1);
                                return ok;
                            } else {
                                return MyResult.err(ResultEnum.LOGIN_ERR);
                            }
            }else{
                return MyResult.err(ResultEnum.PASSWORD_FALSE);
            }
        }else{
            return MyResult.err(ResultEnum.PHONE_FALSE);
        }
    }

//    发送验证码方法
    @GetMapping("/user/getCode")
    @Operation(summary = "发送验证码方法",description = "发送验证码并存放在redis中")
    @Parameters({
            @Parameter(name = "phone",description = "用于redis储存验证码的唯一标识以及接收验证码的手机号")
    })
    public MyResult getCode(String phone) throws Exception {
        boolean b = userService.phoneExits(phone);
        if(b==true){
            String code = userService.getCode(phone);
            if(code!=null){
                return MyResult.ok();
            }else{
                return MyResult.err(ResultEnum.ERR);
            }
        }else{
            return MyResult.err(ResultEnum.PHONE_EXITS);
        }
    }

//    退出方法,删除redis中的用户登录信息以及cookie
    @GetMapping("/user/goOut")
    public MyResult goOut(String phone,HttpServletResponse response){
        Boolean delete = stringRedisTemplate.delete(phone + RedisKey.USER_TOKEN);
        if(delete){
            return MyResult.ok();
        }else{
            return MyResult.err(ResultEnum.REDIS_DELETE_FAIL);
        }
    }
}
