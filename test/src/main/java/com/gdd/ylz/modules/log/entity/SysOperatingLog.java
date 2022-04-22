package com.gdd.ylz.modules.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author xzg
 * @since 2022-04-08
 */
@TableName("SYS_OPERATING_LOG")
@ApiModel(value="SysOperatingLog对象", description="操作日志表")
public class SysOperatingLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private Long id;

    @ApiModelProperty(value = "操作用户主键")
    @TableField("OPERATING_USER_ID")
    private Long operatingUserId;

    @ApiModelProperty(value = "操作用户名称")
    @TableField("OPERATING_USER_NAME")
    private String operatingUserName;

    @ApiModelProperty(value = "操作时间")
    @TableField("OPERATING_TIME")
    private LocalDateTime operatingTime;

    @ApiModelProperty(value = "操作IP")
    @TableField("OPERATING_IP")
    private String operatingIp;

    @ApiModelProperty(value = "操作类型	取字典值")
    @TableField("OPERATING_TYPE")
    private Long operatingType;

    @ApiModelProperty(value = "是否操作成功	Y/N")
    @TableField("IS_SUCCESS")
    private String isSuccess;

    @ApiModelProperty(value = "模块名称	根据业务定义")
    @TableField("MODULE_NAME")
    private String moduleName;

    @ApiModelProperty(value = "浏览器标识")
    @TableField("UA")
    private String ua;

    @ApiModelProperty(value = "接口名称	根据业务定义")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "错误信息	异常的 message 值")
    @TableField("ERROR_MESSAGE")
    private String errorMessage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperatingUserId() {
        return operatingUserId;
    }

    public void setOperatingUserId(Long operatingUserId) {
        this.operatingUserId = operatingUserId;
    }

    public String getOperatingUserName() {
        return operatingUserName;
    }

    public void setOperatingUserName(String operatingUserName) {
        this.operatingUserName = operatingUserName;
    }

    public LocalDateTime getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(LocalDateTime operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getOperatingIp() {
        return operatingIp;
    }

    public void setOperatingIp(String operatingIp) {
        this.operatingIp = operatingIp;
    }

    public Long getOperatingType() {
        return operatingType;
    }

    public void setOperatingType(Long operatingType) {
        this.operatingType = operatingType;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "SysOperatingLog{" +
        "id=" + id +
        ", operatingUserId=" + operatingUserId +
        ", operatingUserName=" + operatingUserName +
        ", operatingTime=" + operatingTime +
        ", operatingIp=" + operatingIp +
        ", operatingType=" + operatingType +
        ", isSuccess=" + isSuccess +
        ", moduleName=" + moduleName +
        ", ua=" + ua +
        ", url=" + url +
        ", errorMessage=" + errorMessage +
        "}";
    }
}
