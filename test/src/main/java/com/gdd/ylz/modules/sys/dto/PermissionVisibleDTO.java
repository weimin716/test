package com.gdd.ylz.modules.sys.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionVisibleDTO  {
    @NotBlank(message = "id 不能为空")
    private String id;

    @NotBlank(message="是否可见不能为空")
    private int visible;
}
