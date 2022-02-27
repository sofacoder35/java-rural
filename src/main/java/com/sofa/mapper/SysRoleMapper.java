package com.sofa.mapper;

import com.github.pagehelper.Page;
import com.sofa.entity.SysPermission;
import com.sofa.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    void insert(SysRole role);

    void update(SysRole role);

    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysRole> findPage(String queryString);

    SysRole findById(Long id);

    void deleteMenuById(@Param("roleId") Long roleId);

    void deletePermissionById(@Param("roleId") Long roleId);

    //添加角色权限信息
    void insertPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void insertMenus(@Param("roleId") Long roleId, @Param("menusId") Long menusId);

    SysRole findByLabel(String label);

    List<SysRole> findAll();
}
