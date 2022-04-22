package com.gdd.ylz.modules.sys.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 平台角色用户关系表
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@ApiModel(value="TPlattRoleUser对象", description="平台角色用户关系表")
@Data
public class TPlattRoleUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String plattUserId;

    @ApiModelProperty(value = "角色id")
    private String plattRoleId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Integer iFlag;



}
