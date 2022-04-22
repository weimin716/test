package com.gdd.ylz.modules.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmailMsgReadDTO {

    private List<String> mailIds;
    private Integer isRead;
    private Integer status;

}
