package com.sofa.controller;


import com.sofa.entity.SysRole;
import com.sofa.entity.SysUser;
import com.sofa.service.SysUserService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "分页查找")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return userService.findPage(queryInfo);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }


    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysUser sysUser){
        return userService.insert(sysUser);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/update")
    public Result update(@RequestBody SysUser sysUser){
        return userService.update(sysUser);
    }

}
