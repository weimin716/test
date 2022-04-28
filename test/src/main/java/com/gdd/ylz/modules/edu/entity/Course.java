package com.gdd.ylz.modules.edu.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@ApiModel(value="Course对象", description="课程表")
@Data
public class Course implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程名")
    private String name;

    @ApiModelProperty(value = "浏览次数")
    private Integer viewCount;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "缩略图所在文件id")
    private String picFileId;

    @ApiModelProperty(value = "所属讲师id")
    private String teacherId;

    @ApiModelProperty(value = "1会员课程2免费课程")
    private Integer type;

    @ApiModelProperty(value = "课程附件id")
    private String fileId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者id")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者id")
    private String updateBy;

    @ApiModelProperty(value = "删除标志位,1未删除，0已删除")
    private Integer iFlag;
}
