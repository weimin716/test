package com.gdd.ylz.modules.timer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务日志表
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
@ApiModel(value="SysTaskLog对象", description="定时任务日志表")
@Data
public class SysTaskLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "任务id")
    private String jobId;

    @ApiModelProperty(value = "名称名称")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    private String jobGroup;

    @ApiModelProperty(value = "任务执行时调用哪个类的方法 包名+类名")
    private String beanName;

    @ApiModelProperty(value = "任务执行时调用上面类的方哪个方法 ")
    private String methodName;

    @ApiModelProperty(value = "日志信息")
    private String message;

    @ApiModelProperty(value = "执行状态 0-正常  1-失败")
    private Integer status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "是否已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建者")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "修改者")
    private Long updatedBy;

    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

}
