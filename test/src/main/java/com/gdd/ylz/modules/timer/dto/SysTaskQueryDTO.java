package com.gdd.ylz.modules.timer.dto;

import com.gdd.ylz.config.mybatisplus.PageFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysTaskQueryDTO extends PageFilter {
    /**
     *通过任务名称模糊查询
     */
    @ApiModelProperty(value = "任务名称")
    private String jobName;
}
