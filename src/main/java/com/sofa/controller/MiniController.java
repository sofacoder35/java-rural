package com.sofa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sofa.entity.SysUser;
import com.sofa.service.SysUserService;
import com.sofa.utils.Result;
import com.sofa.utils.StringUtils;
import com.sofa.vo.WxLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "小程序接口")
@RestController
@RequestMapping("/mini")
@Slf4j
public class MiniController {

    @Value("${mini.appid}")
    private String appid;

    @Value("${mini.secret}")
    private String secret;

    @Autowired
    private SysUserService userService;


    @ApiOperation(value = "登录小程序")
    @PostMapping("/login")
    public Result login(@RequestBody WxLoginVo wxLoginVo) throws IOException {
        String code=wxLoginVo.getCode();
        if (StringUtils.isEmpty(code)) {
            return Result.fail("登陆失败");
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);
        log.info("请求响应码{}",response.getStatusLine().getStatusCode());
        String result = EntityUtils.toString(response.getEntity());
        log.info("请求响应结果{}",result);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        log.info("openid:{}",openid);
        wxLoginVo.setOpenId(openid);
        userService.updateByopenid(wxLoginVo);
        return userService.miniLogin(openid);
    }

//    @ApiOperation(value = "更新用户信息")
//    @PostMapping("/update/info")
//    public Result updateInfo(@RequestBody SysUser user){
//        return  userService.updateByopenid(user);
//    }
}
