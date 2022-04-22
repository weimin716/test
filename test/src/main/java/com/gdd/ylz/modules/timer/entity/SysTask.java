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
 * 定时任务信息表
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
@ApiModel(value="SysTask对象", description="定时任务信息表")
@Data
public class SysTask implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "任务分组")
    private String jobGroup;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务描述")
    private String remaks;

    @ApiModelProperty(value = "任务表达式")
    private String cron;

    @ApiModelProperty(value = "状态 0.正常 1.暂停")
    private Integer status;

    @ApiModelProperty(value = "任务执行时调用哪个类的方法 包名+类名")
    private String beanName;

    @ApiModelProperty(value = "任务执行时调用上面类的方哪个方法 ")
    private String methodName;

    @ApiModelProperty(value = "是否已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "修改者")
    private String updatedBy;

    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

}
