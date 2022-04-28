package com.gdd.ylz.modules.edu.request;

import com.gdd.ylz.modules.edu.base.OperationType;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/28 0028 13:07
 * @description：
 * @modified By：`
 * @version: 1.0
 */
public class CoursePartAddRequest {

    @ApiModelProperty(value = "主键")
    @NotEmpty(message="id不能为空",groups={OperationType.Update.class})
    private String id;

    @ApiModelProperty(value = "课时名称")
    @NotEmpty(message="课时名称不能为空",groups={OperationType.Update.class,OperationType.Insert.class})
    private String name;

    @ApiModelProperty(value = "排序编号")
    private String orderNum;

    @ApiModelProperty(value = "所属课程id")
    private String courseId;

    @ApiModelProperty(value = "视频地址")
    private String fileId;

}
