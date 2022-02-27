package com.sofa.mapper;

import com.github.pagehelper.Page;
import com.sofa.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper {

    void insert(SysPermission permission);

    void update(SysPermission permission);

    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysPermission> findPage(String queryString);


    List<SysPermission> findByRoleId(@Param("roleId") Long roleId);

    List<SysPermission> findAll();
}
