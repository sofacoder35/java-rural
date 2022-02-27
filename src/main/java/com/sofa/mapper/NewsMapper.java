package com.sofa.mapper;

import com.github.pagehelper.Page;
import com.sofa.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {

    @Insert("insert into news(title,content,create_time,create_name,imageUrl) values (#{title},#{content},#{createTime},#{createName},#{imageUrl})")
    void insert(News news);

    void update(News news);

    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<News> findPage(String queryString);

    List<News> findAll();
}
