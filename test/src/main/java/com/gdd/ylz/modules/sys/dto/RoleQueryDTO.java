package com.gdd.ylz.modules.sys.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import lombok.Data;

@Data
public class RoleQueryDTO extends PageFilter {
    private String name;
}
