package com.gdd.ylz.modules.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRespDTO {
    @ApiModelProperty(value = "业务访问token")
    private String accessToken;
    @ApiModelProperty(value = "业务token刷新凭证")
    private String refreshToken;
    @ApiModelProperty(value = "用户id")
    private String userId;
}
