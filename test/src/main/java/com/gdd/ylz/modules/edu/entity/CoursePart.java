package com.gdd.ylz.modules.edu.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程课时表
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@ApiModel(value="CoursePart对象", description="课程课时表")
@Data
public class CoursePart implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课时名称")
    private String name;

    @ApiModelProperty(value = "排序编号")
    private String orderNum;

    @ApiModelProperty(value = "所属课程id")
    private String courseId;

    @ApiModelProperty(value = "视频地址")
    private String fileId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者id")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者id")
    private String updateBy;

    @ApiModelProperty(value = "删除标志位")
    private Integer iFlag;

}
