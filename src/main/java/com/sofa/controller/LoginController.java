package com.sofa.controller;

import com.sofa.service.SysUserService;
import com.sofa.utils.RedisUtil;
import com.sofa.utils.Result;
import com.sofa.utils.SecurityUtil;
import com.sofa.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "用户接口")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        return sysUserService.login(loginVo);
    }

    @ApiOperation(value = "短信登录")
    @PostMapping("/sms/login")
    public Result smsLogin(@RequestBody LoginVo loginVo){
        return sysUserService.login(loginVo);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public Result getUserInfo(){
        return Result.success("获取成功", SecurityUtil.getUser());
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public Result logout(){
        redisUtil.delKey("userInfo"+SecurityUtil.getUsername());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return Result.success("退出成功");
    }

}
