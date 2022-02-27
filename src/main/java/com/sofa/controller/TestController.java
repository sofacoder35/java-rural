package com.sofa.controller;


import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试接口")
public class TestController {
    @ApiOperation(value = "测试Test")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @RequestMapping("/test")
    public Result test(){
        return Result.success("信息返回成功","你好");
    }
}
