package com.sofa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
public class SysRole implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色标签")
    private String label;

    @ApiModelProperty(value = "角色代码")
    private String code;

    @ApiModelProperty(value = "显示状态")
    private boolean status;

    @ApiModelProperty(value = "菜单列表")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "权限列表")
    private List<SysPermission> permissions;



}
