package com.sofa.mapper;

import com.github.pagehelper.Page;
import com.sofa.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    void insert(SysMenu menu);

    void update(SysMenu menu);

    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysMenu> findPage(String queryString);

    List<SysMenu>findParent();

    List<SysMenu> findByRoleId(@Param("roleId") Long roleId);

    List<SysMenu> findByRoleIdAndParentId(@Param("parentId") Long parentId,@Param("roleId") Long roleId);


}
