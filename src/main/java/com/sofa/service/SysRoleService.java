package com.sofa.service;

import com.sofa.entity.SysRole;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;

public interface SysRoleService {
    /**
     * 分页查询
     * @param queryInfo 页码，页数大小，查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);


    Result insert(SysRole role);

    Result delete(Long id);

    Result update(SysRole role);

    Result findAll();
}
