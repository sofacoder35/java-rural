package com.sofa.mapper;

import com.sofa.entity.Collection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {

    void insert(Collection collection);

    List<Collection> findById(Long id);

    void delete(Long id);

}
