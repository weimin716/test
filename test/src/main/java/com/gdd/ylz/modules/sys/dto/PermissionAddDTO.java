package com.gdd.ylz.modules.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PermissionAddDTO implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "权限名称")
    @NotBlank(message="权限名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限描述")
    private String descripion;

    @ApiModelProperty(value = "授权链接")
    private String url;

    @ApiModelProperty(value = "是否跳转 0 不跳转 1跳转")
    private Integer isBlank;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
    @NotBlank(message="权限类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    @NotBlank(message="排序不能为空")
    private Integer orderNum;

    @ApiModelProperty(value = "是否可见")
    @NotBlank(message="是否可见不能为空")
    private Integer visible;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "0删除1未删除")
    private Integer iFlag;

}
