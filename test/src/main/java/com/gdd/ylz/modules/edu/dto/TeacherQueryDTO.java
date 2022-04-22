package com.gdd.ylz.modules.edu.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import lombok.Data;

@Data
public class TeacherQueryDTO extends PageFilter {
    private String name;
}
