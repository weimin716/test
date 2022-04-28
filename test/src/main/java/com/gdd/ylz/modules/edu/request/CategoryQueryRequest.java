package com.gdd.ylz.modules.edu.request;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：weimin
 * @date ：Created in 2022/4/28 0028 9:43
 * @description：查询实体类
 * @modified By：`
 * @version: 1.0
 */
@Data
public class CategoryQueryRequest extends PageFilter {
    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "上级分类id")
    private String pid;

}
