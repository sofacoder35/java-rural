package com.sofa.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "我的收集")
public class Collection {

    private Integer id;

    private Integer userId;

    private String plantName;

    private String url;

    private String saveTime;
}
