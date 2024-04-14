package com.example.ylbweb.Controller;

import com.example.ylbweb.BaseController.BaseController;
import com.example.ylbweb.view.MyResult;
import com.ylbApi.Enum.ResultEnum;
import com.ylbApi.Pojo.BBidInfo;
import com.ylbApi.Pojo.BLoanInfo;
import com.ylbApi.PojoUtil.IndexPageLimit;
import com.ylbApi.PojoUtil.MybatisKeyPropertyUtil;
import com.ylbApi.PojoUtil.Page;
import com.ylbApi.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1")
@Tag(name="商品相关类",description = "对应b_loan_info表")
public class ProductController extends BaseController {


    @Operation(summary = "获取首页全部分页商品信息",description = "1个新手产品，3个优选和散标产品")
    @GetMapping("/product/index")
    public MyResult getlimitProduct(){
        IndexPageLimit allLimitProduct = productService.getAllLimitProduct();
        if(productService!=null) {
            MyResult ok = MyResult.ok();
            ok.setData(allLimitProduct);
            return ok;
        }else {
            return MyResult.err(ResultEnum.ERR_NULL);
        }
    }

    @Operation(summary = "获取更多商品信息",description = "点击更多产品后的商品分页查询方法")
    @GetMapping("/product/list")
    @Parameters({
            @Parameter(name = "pType",description = "查询商品的类型"),
            @Parameter(name = "pageNo",description = "查询的页数"),
            @Parameter(name = "pType",description = "每页多少商品")
    })
    public MyResult getMoreLimitProduct(
            @RequestParam(value = "pType",required = true) Integer pType,
            @RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = "9") Integer pageSize
    ){
        if (pType==0||pType==1||pType==2) {
            Integer count = productService.getProductTypeCount(pType);
            if (count>0){
                List<BLoanInfo> limitProduct = productService.getLimitProduct(pType, pageNo, pageSize);
                MyResult ok = MyResult.ok();
                ok.setData(limitProduct);
                Page page=new Page(pageSize,count,pageNo);
                ok.setPage(page);
                return ok;
            }else{
                return MyResult.err(ResultEnum.ERR_NULL);
            }
        }else{
            return MyResult.err(ResultEnum.ERR_PARAM);
        }
    }


    @Operation(summary = "根据商品id查询交易记录",description = "用于商品详情页面")
    @Parameters({
            @Parameter(name = "productId",description = "商品id")
    })
    @GetMapping("/product/traffic")
    public MyResult getProductTraffic(Integer productId){
        List<BBidInfo> productTraffic = productService.getProductTraffic(productId);
        if (productTraffic.size()>0){
            MyResult ok = MyResult.ok();
            ok.setData(productTraffic);
            return ok;
        }else{
            return MyResult.err(ResultEnum.ERR_NULL);
        }
    }

    @GetMapping("/product/touzi")
    @Operation(summary = "用户投资方法",description = "涉及生成投资，收益单，改变商品可投资金额，改变用户账户金额")
    @Parameters({
            @Parameter(name = "phone",description = "当前用户的电话号码",required = true),
            @Parameter(name = "productId",description = "投资商品的id",required = true),
            @Parameter(name = "money",description = "用户投资的金额",required = true),
            @Parameter(name = "incomeMoney",description = "用户收益的金额",required = true),
            @Parameter(name = "cycle",description = "产品收益时间，除新手宝为天外，都是月单位",required = true)
    })
    @Transactional
    public MyResult touzi(@RequestParam("phone") String phone,
                          @RequestParam("productId") Integer productId,
                          @RequestParam("money") BigDecimal money,
                          @RequestParam("incomeMoney")BigDecimal incomeMoney,
                          @RequestParam("cycle") Integer cycle){
        //获取用户id
        Integer uidByPhone = centerService.getUidByPhone(phone);
        //生成投资单
        MybatisKeyPropertyUtil mybatisKeyPropertyUtil = new MybatisKeyPropertyUtil(0);
        int touzi = productService.maketouzi(productId,uidByPhone,money,mybatisKeyPropertyUtil);
        //生成收益单
        int shouyi = productService.makeshouyi(productId,uidByPhone,money,incomeMoney,cycle, mybatisKeyPropertyUtil.getId());
        //改变商品可投资金额
        int update1=productService.setLeftMoney(productId,money);
        //改变用户账户金额
        int update2=productService.setAccountMoney(uidByPhone,money);
        if(touzi>0&&shouyi>0&&update1>0&&update2>0){
            return MyResult.ok();
        }else {
            MyResult err = MyResult.err(ResultEnum.TOUZI_FALSE);
            return err;
        }
    }
}
