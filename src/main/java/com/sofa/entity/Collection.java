package com.sofa.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "ζηζΆι")
public class Collection {

    private Integer id;

    private Integer userId;

    private String plantName;

    private String url;

    private String saveTime;
}
