package com.gdd.ylz.modules.login.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterDTO  {
    @NotEmpty(message="用户名不能为空")
    private String username;
    @NotEmpty(message="密码不能为空")
    private String password;
    @NotEmpty(message="手机号码不能为空")
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String vcode;
    @NotEmpty(message = "验证码不能为空")
    private String nickname;

}
