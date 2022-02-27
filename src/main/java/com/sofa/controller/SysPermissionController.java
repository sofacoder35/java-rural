package com.sofa.controller;

import com.sofa.entity.SysPermission;
import com.sofa.service.SysPermissionService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@Api(tags = "权限数据")
public class SysPermissionController {
    @Autowired
    private SysPermissionService permissionService;

    @ApiOperation(value = "分页查找")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return permissionService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加权限")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysPermission sysPermission){
        return permissionService.insert(sysPermission);
    }

    @ApiOperation(value = "修改权限")
    @PutMapping("/update")
    public Result update(@RequestBody SysPermission sysPermission){
        return permissionService.update(sysPermission);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

    @ApiOperation(value = "查询所有权限")
    @GetMapping("/findAll")
    public Result findAll(){
        return permissionService.findAll();
    }






}
