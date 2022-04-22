package com.gdd.ylz.modules.sys.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionQueryDTO extends PageFilter implements Serializable {
  @ApiModelProperty(value="菜单名称")
  private String name;
  @ApiModelProperty(value="上级菜单名称")
  private String pname;
}
