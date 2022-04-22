package com.gdd.ylz.modules.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactMoveDTO {
    private List<String> ids;
    private String groupId;

}
