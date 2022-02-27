package com.sofa.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录参数")
public class LoginVo {
    @ApiModelProperty(value = "用户名" ,dataType = "String")
    private String username;

    @ApiModelProperty(value = "密码" ,dataType = "String")
    private String password;

    @ApiModelProperty(value = "电话号码" ,dataType = "String")
    private String phoneNumber;

    @ApiModelProperty(value = "验证码" ,dataType = "String")
    private String code;

    @ApiModelProperty(value = "1账号登入，2验证码登入")
    public int type;
}
