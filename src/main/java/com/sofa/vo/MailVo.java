package com.sofa.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "邮件发送内容")
public class MailVo implements Serializable {

    @ApiModelProperty(value = "是否是html格式")
    private boolean html=false;

    @ApiModelProperty(value = "接收人")
    private String[] receivers;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;


}
