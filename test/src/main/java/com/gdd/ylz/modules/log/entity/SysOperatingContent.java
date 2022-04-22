package com.gdd.ylz.modules.log.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 操作内容表
 * </p>
 *
 * @author xzg
 * @since 2022-04-08
 */
@TableName("SYS_OPERATING_CONTENT")
@ApiModel(value="SysOperatingContent对象", description="操作内容表")
public class SysOperatingContent implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private BigDecimal id;

    @ApiModelProperty(value = "父级主键	")
    @TableField("P_ID")
    private BigDecimal pId;

    @ApiModelProperty(value = "操作参数")
    @TableField("OPERATING_PARAMETER")
    private String operatingParameter;

    @ApiModelProperty(value = "返回值	void -> 无")
    @TableField("OPERATING_RESULT")
    private String operatingResult;

    @ApiModelProperty(value = "错误堆栈	")
    @TableField("ERROR_TRACK")
    private String errorTrack;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getpId() {
        return pId;
    }

    public void setpId(BigDecimal pId) {
        this.pId = pId;
    }

    public String getOperatingParameter() {
        return operatingParameter;
    }

    public void setOperatingParameter(String operatingParameter) {
        this.operatingParameter = operatingParameter;
    }

    public String getOperatingResult() {
        return operatingResult;
    }

    public void setOperatingResult(String operatingResult) {
        this.operatingResult = operatingResult;
    }

    public String getErrorTrack() {
        return errorTrack;
    }

    public void setErrorTrack(String errorTrack) {
        this.errorTrack = errorTrack;
    }

    @Override
    public String toString() {
        return "SysOperatingContent{" +
        "id=" + id +
        ", pId=" + pId +
        ", operatingParameter=" + operatingParameter +
        ", operatingResult=" + operatingResult +
        ", errorTrack=" + errorTrack +
        "}";
    }
}
