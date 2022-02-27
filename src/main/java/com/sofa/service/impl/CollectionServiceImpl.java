package com.sofa.service.impl;

import com.sofa.entity.Collection;
import com.sofa.mapper.CollectionMapper;
import com.sofa.service.CollectionService;
import com.sofa.utils.DateUtils;
import com.sofa.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Value("${qiniu.baseUrl}")
    private String baseUrl;

    @Override
    public Result insert(Collection collection) {
        collection.setUrl(baseUrl+collection.getUrl());
        collection.setSaveTime(DateUtils.getDateTime());
        collectionMapper.insert(collection);
        return Result.success("添加成功");
    }

    @Override
    public Result delete(Long id) {
        collectionMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result findById(Long id) {
        return Result.success("查询收集成功",collectionMapper.findById(id));
    }
}
