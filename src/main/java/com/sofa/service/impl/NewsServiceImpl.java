package com.sofa.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sofa.entity.News;
import com.sofa.mapper.NewsMapper;
import com.sofa.service.NewsService;
import com.sofa.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始菜单数据分页-->页码{},-->页数{},-->查询内容{}",queryInfo.getPageNumber(),queryInfo.getPageSize(),queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<News> page = newsMapper.findPage(queryInfo.getQueryString());
        long total=page.getTotal();
        List<News> result = page.getResult();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表-->{}",result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(News news) {
        news.setCreateName(SecurityUtil.getUsername());
        news.setCreateTime(DateUtils.getDateTime());
        newsMapper.insert(news);
        return Result.success("添加成功");
    }

    @Override
    public Result delete(Long id) {
        if(id==null)return Result.fail("id为空");
        newsMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result update(News news) {
        news.setUpdateName(SecurityUtil.getUsername());
        news.setUpdateTime(DateUtils.getDateTime());
        newsMapper.update(news);
        return Result.success("修改成功");
    }

    @Override
    public Result findAll() {
        return  Result.success("查找全部成功", newsMapper.findAll());
    }


}
