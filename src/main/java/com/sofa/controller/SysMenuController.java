package com.sofa.controller;

import com.sofa.entity.SysMenu;
import com.sofa.entity.SysPermission;
import com.sofa.service.SysMenuService;
import com.sofa.service.SysPermissionService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@Api(tags = "菜单数据")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;

    @ApiOperation(value = "查询父级菜单")
    @GetMapping("/findParent")
    public Result findParent(){
        return menuService.findParent();
    }


    @ApiOperation(value = "分页查找")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return menuService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysMenu sysMenu){
        return menuService.insert(sysMenu);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public Result update(@RequestBody  SysMenu sysMenu){
        return menuService.update(sysMenu);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return menuService.delete(id);
    }






}
