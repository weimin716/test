package com.gdd.ylz.modules.mail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 邮件信息
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@TableName("mail_msg")
@ApiModel(value="Msg对象", description="邮件信息")
public class Msg implements Serializable {

    private static final long serialVersionUID=1L;

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


    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromEmails() {
        return fromEmails;
    }

    public void setFromEmails(String fromEmails) {
        this.fromEmails = fromEmails;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public String getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(String ccEmails) {
        this.ccEmails = ccEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFreemarkerTemplate() {
        return freemarkerTemplate;
    }

    public void setFreemarkerTemplate(String freemarkerTemplate) {
        this.freemarkerTemplate = freemarkerTemplate;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToNames() {
        return toNames;
    }

    public void setToNames(String toNames) {
        this.toNames = toNames;
    }

    public String getCcNames() {
        return ccNames;
    }

    public void setCcNames(String ccNames) {
        this.ccNames = ccNames;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Msg{" +
        "id=" + id +
        ", fromEmails=" + fromEmails +
        ", toEmails=" + toEmails +
        ", ccEmails=" + ccEmails +
        ", subject=" + subject +
        ", content=" + content +
        ", freemarkerTemplate=" + freemarkerTemplate +
        ", attachments=" + attachments +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", sendTime=" + sendTime +
        ", status=" + status +
        ", type=" + type +
        ", fromName=" + fromName +
        ", toNames=" + toNames +
        ", ccNames=" + ccNames +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        "}";
    }
}
