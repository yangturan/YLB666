package com.example.ylbweb.Controller;

import com.example.ylbweb.BaseController.BaseController;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.PojoUtil.PlatBaseInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/v1")
@Tag(name = "平台基本信息类",description = "提供平台的年利率统计，用户统计等")
public class PlatController extends BaseController {

    @GetMapping("/plat/info")
    @Operation(summary = "平均年利率，总用户，总成交额获取方法",description = "顺序需要自己判断")
    public MyResult getPlatInfo(){
        PlatBaseInfo platBaseInfo = platBaseInfoService.getPlatBaseInfo();
//        200是成功码
        MyResult ok = MyResult.ok();
        ok.setData(platBaseInfo);
        return ok;
    }


}
