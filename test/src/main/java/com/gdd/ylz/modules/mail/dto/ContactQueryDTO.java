package com.gdd.ylz.modules.mail.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import lombok.Data;

@Data
public class ContactQueryDTO extends PageFilter {
    private String name;
    private String groupId;
}
