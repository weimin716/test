package com.gdd.ylz.modules.sys.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TSysUserQueryDTO extends PageFilter {

    @ApiModelProperty(value="用户名")
    private String username;

    @ApiModelProperty(value="手机号")
    private String phone;

}
