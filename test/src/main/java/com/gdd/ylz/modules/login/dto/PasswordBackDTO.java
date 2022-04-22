package com.gdd.ylz.modules.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class PasswordBackDTO {
    @ApiModelProperty(value="绑定手机号")
    @NotBlank(message = "绑定手机号不能为空")
    private String phone;

    @ApiModelProperty(value="图形验证码")
    @NotBlank(message = "图形验证码不能为空")
    private String captcha;

    @ApiModelProperty(value="手机验证码")
    @NotBlank(message = "手机验证码不能为空")
    private String vcode;

    @ApiModelProperty(value="新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;




}
