package com.gdd.ylz.modules.edu.request;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import com.gdd.ylz.modules.edu.base.OperationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/28 0028 10:42
 * @description：
 * @modified By：`
 * @version: 1.0
 */
@Data
public class CoursePartQueryRequest extends PageFilter {

    @ApiModelProperty(value = "课时名称")
    private String name;

    @ApiModelProperty(value = "所属课程id")
    @NotEmpty(message ="课程id不能为空",groups = {OperationType.Page.class})
    private String courseId;


}
