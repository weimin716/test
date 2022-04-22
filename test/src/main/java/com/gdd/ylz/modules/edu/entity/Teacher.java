package com.gdd.ylz.modules.edu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 讲师表
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@ApiModel(value="Teacher对象", description="讲师表")
@Data
public class Teacher implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "工作年限(整数)")
    private Integer workTime;

    @ApiModelProperty(value = "简介")
    private String info;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者id")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者id")
    private String updateBy;

    @ApiModelProperty(value="0删除1未删除")
    private Integer iFlag;


}
