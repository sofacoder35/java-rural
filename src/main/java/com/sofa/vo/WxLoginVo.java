package com.sofa.vo;

import lombok.Data;

@Data
public class WxLoginVo {

    private String code;

    private String nickName;

    private String avatar;

    private Long sex;

    private String address;

    private String openId;

}
