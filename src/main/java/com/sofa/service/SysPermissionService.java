package com.sofa.service;

import com.sofa.entity.SysPermission;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;

public interface SysPermissionService {
    /**
     * 分页查询
     * @param queryInfo 页码，页数大小，查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    Result insert(SysPermission sysPermission);

    Result delete(Long id);

    Result update(SysPermission sysPermission);

    //查询所有权限
    Result findAll();
}
