package com.sofa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author sofa
 * @since 2022-01-19
 */
@Data

public class SysMenu implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "前端路由")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "显示状态")
    private boolean status;

    @ApiModelProperty(value = "父级菜单")
    private Long parentId;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;


}
