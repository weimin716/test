package com.gdd.ylz.modules.mail.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LayeditPicDTO {
    @ApiModelProperty(value = "图片路径")
    private String src;
    @ApiModelProperty(value = "图片名称")
    private String title;
}
