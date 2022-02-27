package com.sofa.controller;

import com.sofa.entity.SysPermission;
import com.sofa.entity.SysRole;
import com.sofa.service.SysRoleService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        return roleService.findAll();
    }

    @ApiOperation(value = "分页查找")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return roleService.findPage(queryInfo);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return roleService.delete(id);
    }


    @ApiOperation(value = "添加角色")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysRole sysRole){
        return roleService.insert(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole sysRole){
        return roleService.update(sysRole);
    }

}
