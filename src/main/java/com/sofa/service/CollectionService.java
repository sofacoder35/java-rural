package com.sofa.service;

import com.sofa.entity.Collection;
import com.sofa.utils.Result;

public interface CollectionService {


    Result insert(Collection collection);

    Result delete(Long id);

    Result findById(Long id);
}
