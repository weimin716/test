package com.gdd.ylz.modules.edu.request;

import com.gdd.ylz.modules.edu.base.OperationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/27 0027 15:41
 * @description：
 * @modified By：`
 * @version: 1.0
 */
@Data
public class CourseAddRequest {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程名")
    @NotEmpty(message = "课程名不能为空",groups = {OperationType.Insert.class})
    private String name;

    @ApiModelProperty(value = "浏览次数")
    private Integer viewCount;

    @ApiModelProperty(value = "分类id")
    @NotEmpty(message = "课程分类不能为空",groups = {OperationType.Insert.class})
    private String categoryId;

    @ApiModelProperty(value = "缩略图所在文件id")
    @NotEmpty(message = "课程缩略图不能为空",groups = {OperationType.Insert.class})
    private String picFileId;

    @ApiModelProperty(value = "所属讲师id")
    @NotEmpty(message = "所属讲师不能为空",groups = {OperationType.Insert.class})
    private String teacherId;

    @ApiModelProperty(value = "1会员课程2免费课程")
    @NotEmpty(message = "课程类型不能为空",groups = {OperationType.Insert.class})
    private Integer type;

    @ApiModelProperty(value = "课程附件id")
    private String fileId;
}
