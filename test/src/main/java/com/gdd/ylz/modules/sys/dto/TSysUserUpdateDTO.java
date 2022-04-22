package com.gdd.ylz.modules.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TSysUserUpdateDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户类型（00系统用户 01注册用户）")
    private String userType;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "部门id")
    private Long depId;

    @ApiModelProperty(value = "岗位id")
    private Long posId;

    @ApiModelProperty(value = "手机验证码")
    private Long vcode;

    @ApiModelProperty(value = "角色Id串")
    private String roleIds;
}
