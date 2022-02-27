package com.sofa.service;

import com.sofa.entity.SysMenu;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;

public interface SysMenuService {
    /**
     * 分页查询
     * @param queryInfo 页码，页数大小，查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    Result findParent();

    Result insert(SysMenu menu);

    Result delete(Long id);

    Result update(SysMenu menu);
}
