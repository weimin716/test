package com.gdd.ylz.modules.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
@Data
public class LoginPassWordDTO {

    @NotBlank(message="用户名或手机号不能为空")
    private String usernameorphone;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message="验证码不能为空")
    private String captcha;
    @NotBlank(message="key值不能为空")
    private String key;
}
