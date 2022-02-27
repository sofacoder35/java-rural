package com.sofa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sofa.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;

/**
 * <p>
 * 
 * </p>
 *
 * @author sofa
 * @since 2022-01-19
 */
@Data
public class SysUser implements UserDetails {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "前端展示用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Long sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "微信唯一id")
    private String openId;

    @ApiModelProperty(value = "当前状态")
    private Boolean status;

    @ApiModelProperty(value = "是否是管理员")
    private Boolean admin;

    @ApiModelProperty(value = "电话")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色信息")
    private List<SysRole> roles;

    @ApiModelProperty(value = "用户对应菜单列表")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "用户对应权限数据")
    private List<SysPermission> permissions;



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>list=new ArrayList<>();
        if(roles!=null && roles.size()>0){
            roles.forEach(item -> {
                if(StringUtils.isNotEmpty(item.getCode())){
                    list.add(new SimpleGrantedAuthority("ROLE_"+item.getCode()));
                }

            });
        }
        if(permissions!=null && permissions.size()>0){
            permissions.forEach(item->{
                if(StringUtils.isNotEmpty(item.getCode())){
                    list.add(new SimpleGrantedAuthority(item.getCode()));
                }

            });
        }


        return list;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
