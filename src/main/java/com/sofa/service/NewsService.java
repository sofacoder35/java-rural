package com.sofa.service;

import com.sofa.entity.News;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;

public interface NewsService {
    /**
     * 分页查询
     * @param queryInfo 页码，页数大小，查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    Result insert(News news);

    Result delete(Long id);

    Result update(News news);

    Result findAll();
}
