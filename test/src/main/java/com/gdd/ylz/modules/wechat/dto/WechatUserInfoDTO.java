package com.gdd.ylz.modules.wechat.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WechatUserInfoDTO {
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String type;
    private Date modified;

}
