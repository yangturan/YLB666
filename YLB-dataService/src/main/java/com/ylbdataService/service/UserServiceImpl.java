package com.ylbdataService.service;

import com.aliyun.tea.TeaException;
import com.commons.MD5Util;
import com.commons.contants.RedisKey;
import com.ylbApi.Pojo.UUser;
import com.ylbApi.Service.UserService;
import com.ylbdataService.mapper.UUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@DubboService(interfaceClass = UserService.class,version = "1.0.0")
@Transactional
@CacheConfig
public class UserServiceImpl implements UserService {
    @Autowired
    private UUserMapper userMapper;

    @Value("${ylb.passwordsalt}")
    private String salt;

    @Value("${ylb.akey}")
    private String akey;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //    验证注册的手机号码是否存在的方法
    @Override
    public boolean phoneExits(String phone) {
        List<UUser> users = userMapper.phoneExits(phone);
        if (users.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int registry(String phone, String password) {
        String ylb = MD5Util.hmacSign(password + salt, akey);
        password = ylb;
        int i = userMapper.registry(phone, password);
        return i;
    }

    @Override
    public int login(String phone, String loginPassword) {
        String ylb = MD5Util.hmacSign(loginPassword + salt, akey);
        loginPassword = ylb;
        List<UUser> users = userMapper.login(phone, loginPassword);
        if (users.size() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //    验证码平台授权方法
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    //    生成并获取验证码方法并储存到redis
    @Override
    public String getCode(String phone) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。

        //这里的createClient之前的是你的类名
        com.aliyun.dysmsapi20170525.Client client = UserServiceImpl.createClient(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));

        //生成随机四位验证码rand
        Random random = new Random();
        int i = random.nextInt(8999) + 1000;
        String rand = String.valueOf(i);

        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                //你的签名，模板，验证码等信息
                .setSignName("bootStudy")
                .setTemplateCode("SMS_465165853")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":" + rand + "}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        stringRedisTemplate.opsForValue().set(phone+RedisKey.CODE_NUMBER,rand,300, TimeUnit.SECONDS);
        return rand;
    }

    @Override
    public int headerImageUp(String path,String phone) {
        return userMapper.headerImageUp(path,phone);
    }

    @Override
    public String getHeaderImage(String phone) {
        return userMapper.getHeaderImage(phone);
    }

    @Override
    public int makeLastDate(String phone) {
        return userMapper.makeLastTime(phone);
    }
}
