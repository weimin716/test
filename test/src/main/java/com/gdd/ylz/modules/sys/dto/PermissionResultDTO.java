package com.gdd.ylz.modules.sys.dto;

import com.gdd.ylz.modules.sys.entity.TSysPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class PermissionResultDTO {

    @ApiModelProperty(value="本身权限信息")
    private TSysPermission selfTsysPermission;
    @ApiModelProperty(value="子权限集合")
    private List<PermissionResultDTO> sonTsysPermission;
}
