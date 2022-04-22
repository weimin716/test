package com.gdd.ylz.modules.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class LayuiDtreeDTO {
    private String title;
    private String id;
    private String field;
    private List<LayuiDtreeDTO> children;

}
