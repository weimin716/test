package com.gdd.ylz.modules.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserDetailDTO {
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
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "部门id")
    private Long depId;

    @ApiModelProperty(value = "岗位id")
    private Long posId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "盐值加密使用")
    private String salt;

    @ApiModelProperty(value = "逻辑删除 1存在，0删除")
    private Integer iFlag;

    @ApiModelProperty(value = "1正常，2禁用，3注销")
    private Integer status;

    @ApiModelProperty(value="角色id集合")
    private List<String> roleIds;
}
