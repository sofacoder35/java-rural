package com.sofa.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sofa.entity.SysMenu;
import com.sofa.entity.SysPermission;
import com.sofa.entity.SysRole;
import com.sofa.mapper.SysMenuMapper;
import com.sofa.mapper.SysPermissionMapper;
import com.sofa.mapper.SysRoleMapper;
import com.sofa.service.SysRoleService;
import com.sofa.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始菜单数据分页-->页码{},-->页数{},-->查询内容{}",queryInfo.getPageNumber(),queryInfo.getPageSize(),queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysRole> page = roleMapper.findPage(queryInfo.getQueryString());
        long total=page.getTotal();
        List<SysRole> result = page.getResult();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表-->{}",result);
        result.forEach(item->{
            //查询角色下菜单信息
            List<SysMenu>menus=menuMapper.findByRoleId(item.getId());
            menus.forEach(menu->{
                List<SysMenu> children=menuMapper.findByRoleIdAndParentId(menu.getId(), item.getId());
                menu.setChildren(children);
            });
            item.setMenus(menus);
            //查询该角色权限信息
          List<SysPermission> permissions= permissionMapper.findByRoleId(item.getId());
          item.setPermissions(permissions);
        });
        return new PageResult(total,result);
    }

    @Transactional
    @Override
    public Result insert(SysRole role) {
        log.info("查询角色信息是否存在");
        SysRole role1 =roleMapper.findByLabel(role.getLabel());
        if(role1!=null){
           return Result.fail("该角色已经存在");
        }
        roleMapper.insert(role);
        if (role.getPermissions().size()>0) {
            log.info("添加对应权限数据");
            role.getPermissions().forEach(item->roleMapper.insertPermission(role.getId(),item.getId()));

        }
        if (role.getMenus().size()>0) {
            role.getMenus().forEach(item->roleMapper.insertMenus(role.getId(),item.getId()));
        }
        redisUtil.delKey("userInfo_"+ SecurityUtil.getUsername());
        return Result.success("添加角色信息成功");
    }

    @Transactional
    @Override
    public Result delete(Long id) {
        log.info("查询该菜单下是否有菜单信息");
        if(menuMapper.findByRoleId(id).size()>0){
            return Result.fail("删除失败，请先删除对应菜单信息");
        }
        if(permissionMapper.findByRoleId(id).size()>0){
            return Result.fail("删除失败，请先删除对应权限信息");
        }
        redisUtil.delKey("userInfo_"+ SecurityUtil.getUsername());
        roleMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result update(SysRole role) {
        roleMapper.update(role);
        if (role.getPermissions().size()>0) {
            log.info("先删除对应权限数据");
            roleMapper.deletePermissionById(role.getId());
            log.info("再添加对应权限数据");
            role.getPermissions().forEach(item->roleMapper.insertPermission(role.getId(),item.getId()));

        }
        if (role.getMenus().size()>0) {
            roleMapper.deleteMenuById(role.getId());
            role.getMenus().forEach(item->roleMapper.insertMenus(role.getId(),item.getId()));
        }
        redisUtil.delKey("userInfo_"+ SecurityUtil.getUsername());
        return Result.success("修改角色信息成功");
    }

    @Override
    public Result findAll() {
        return Result.success("查询所有角色成功",roleMapper.findAll());
    }

}
