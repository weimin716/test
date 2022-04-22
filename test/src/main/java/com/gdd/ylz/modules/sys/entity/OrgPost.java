package com.gdd.ylz.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzg
 * @since 2022-04-22
 */
@TableName("SYS_ORG_POST")
@ApiModel(value="OrgPost对象", description="")
public class OrgPost implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private Long id;

    @TableField("POST_NAME")
    private String postName;

    @TableField("POST_CODE")
    private String postCode;

    @TableField("ORG_ID")
    private Long orgId;

    @TableField("USER_ID")
    private Long userId;

    @TableField("REMARK")
    private String remark;

    @TableField("DEL_FLAG")
    private String delFlag;

    @TableField("CREATED_BY")
    private String createdBy;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("UPDATED_BY")
    private String updatedBy;

    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrgPost{" +
        "id=" + id +
        ", postName=" + postName +
        ", postCode=" + postCode +
        ", orgId=" + orgId +
        ", userId=" + userId +
        ", remark=" + remark +
        ", delFlag=" + delFlag +
        ", createdBy=" + createdBy +
        ", createTime=" + createTime +
        ", updatedBy=" + updatedBy +
        ", updateTime=" + updateTime +
        "}";
    }
}
