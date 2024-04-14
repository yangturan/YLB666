package com.example.ylbweb.Controller;

import com.example.ylbweb.BaseController.BaseController;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.Enum.ResultEnum;
import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BIncomeRecord;
import com.ylbApi.Pojo.BRechargeRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eclipse.jetty.http.HttpHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//个人中心页面的服务器方法
@RestController
@RequestMapping("/v1")
@CrossOrigin
@Transactional
@Tag(name = "用户中心界面",description = "提供了用户中心界面的各种所需方法")
public class CenterController extends BaseController {

//获取当前用户收益，投资，充值的最近记录
    @Operation(summary = "最近记录获取方法",description = "获取当前用户收益，投资，充值的最近记录")
    @Parameters({
            @Parameter(name = "phone",description = "当前用户电话号码")
    })
    @GetMapping("/center/record")
    public MyResult getRecord(String phone){
        Integer uid = centerService.getUidByPhone(phone);
        List<BBidInfo> bids = centerService.getBid(uid);
        List<BIncomeRecord> incomes = centerService.getIncome(uid);
        List<BRechargeRecord> recharges = centerService.getRecharge(uid);
        List<List> record=new ArrayList<>();
        record.add(bids);
        record.add(incomes);
        record.add(recharges);
        MyResult ok = MyResult.ok();
        ok.setData(record);
        return ok;
    }


    @Operation(summary = "上传用户头像方法",description = "上传头像到服务器地址并把这个地址上传到mysql中")
    @Parameters({
            @Parameter(name = "up",description = "上传的头像文件")
    })
    @PostMapping("/center/image")
    public MyResult upImg(@RequestParam("img") MultipartFile file,@RequestParam("phone")String phone){
        try {
//获取传来的文件的参数名称
            String name = file.getName();
            String originalFilename =file.getOriginalFilename();//获取文件原始名称
//得到后缀名
            String substring="";
            if(originalFilename != null) {
                substring = originalFilename.substring(originalFilename.indexOf("."));
                if(!substring.equals(".jpg")){
                    return MyResult.err(ResultEnum.IMAGE_FALSE);
                }
            }else{
                return MyResult.err(ResultEnum.FILE_NULL);
            }
            String s = UUID. randomUUID () . toString();
            var filename=s+substring;
            var path="E://AllServerImg/"+filename;
            int i=userService.headerImageUp(path,phone);
            if(i>0){
                file.transferTo(new File(path));
                return MyResult.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return MyResult.err(ResultEnum.ERR);
    }

    @GetMapping("/center/img")
    @Operation(summary = "加载用户头像放大",description = "根据路径找到图片并通过字节流和RequestEntity<byte[]>返回给前端")
    @Parameters({
            @Parameter(name = "phone",description = "用户电话号码")
    })
    public ResponseEntity<byte[]> getImage(String phone) throws IOException {
        String path=userService.getHeaderImage(phone);
        if(path==null){
            return null;
        }
        File file=new File(path);
        if(!file.exists()){
            return null;
        }
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        FileInputStream fileInputStream =null;
        fileInputStream = new FileInputStream(file);
        byte[] bytes= new byte[(int)file.length()];
        fileInputStream.read(bytes);
        ResponseEntity responseEntity=new ResponseEntity<byte[]>(bytes,httpHeaders, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/center/money")
    @Operation(summary = "获取用户账户金额方法",description = "通过电话号码获取用户的账户金额")
    @Parameters({
            @Parameter(name = "phone",description = "当前用户的电话号码")
    })
    public MyResult getAccountMoney(String phone){
        Integer uidByPhone = centerService.getUidByPhone(phone);
        BigDecimal accountMoney = centerService.getAccountMoney(uidByPhone);
        if(uidByPhone!=null&&accountMoney!=null){
            MyResult myResult=MyResult.ok();
            myResult.setData(accountMoney);
            return myResult;
        }else{
            return MyResult.err(ResultEnum.ERR);
        }
    }

    @GetMapping("/center/date")
    @Operation(summary = "获取用户最近登录时间",description = "根据电话号码确定用户")
    @Parameters({
            @Parameter(name = "phone",description = "当前用户的电话号码")
    })
    public MyResult getUserBeforeDate(String phone){
        String date = centerService.getUserBeforeDate(phone);
        if(date!=null){
            MyResult ok=MyResult.ok();
            ok.setData(date);
            return ok;
        }else{
            return MyResult.err(ResultEnum.ERR);
        }
    }
}
