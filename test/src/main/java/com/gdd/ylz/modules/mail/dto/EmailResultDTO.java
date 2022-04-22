package com.gdd.ylz.modules.mail.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
public class EmailResultDTO implements Serializable {

    private static final long serialVersionUID = -808318905357852929L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "send")
    private String fromEmails;

    @ApiModelProperty(value = "收件人，多个以分号;隔开")
    private String toEmails;

    @ApiModelProperty(value = "cc,多个以;隔开")
    private String ccEmails;

    @ApiModelProperty(value = "新文件名称")
    private String subject;

    @ApiModelProperty(value = "文件后缀")
    private String content;

    @ApiModelProperty(value = "模板")
    @TableField("freemarkerTemplate")
    private String freemarkerTemplate;

    @ApiModelProperty(value = "att，多个以分号;隔开")
    private String attachments;

    @ApiModelProperty(value = "att，多个以分号;隔开")
    private List<String> attachmentsIds;

    @ApiModelProperty(value = "开始时间")
    @TableField("start_Date")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_Date")
    private Date endDate;

    @ApiModelProperty(value = "send时间")
    @TableField("send_Time")
    private Date sendTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "0 正常 1 定时")
    private Integer type;

    @ApiModelProperty(value = "发送名加地址")
    @TableField("from_Name")
    private String fromName;

    @ApiModelProperty(value = "收件名加地址")
    @TableField("to_Names")
    private String toNames;

    @ApiModelProperty(value = "cc名加地址")
    @TableField("cc_Names")
    private String ccNames;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

}
