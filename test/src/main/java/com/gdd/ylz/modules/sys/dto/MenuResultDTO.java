package com.gdd.ylz.modules.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class MenuResultDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "权限名称")
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
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "子菜单")
    private List<MenuResultDTO> children;

}
