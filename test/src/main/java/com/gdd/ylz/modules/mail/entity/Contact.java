package com.gdd.ylz.modules.mail.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 邮件信息
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@ApiModel(value="Contact对象", description="邮件信息")
@ContentRowHeight(12)
@HeadRowHeight(15)
public class Contact implements Serializable {

    private static final long serialVersionUID=1L;
    @ExcelProperty(value = "ID")
    @ApiModelProperty(value = "主键")
    @ColumnWidth(30)
    private String id;

    @ExcelProperty(value = "姓名")
    @ApiModelProperty(value = "姓名")
    @ColumnWidth(20)
    private String name;

    @ExcelProperty(value = "地址")
    @ApiModelProperty(value = "地址")
    @ColumnWidth(20)
    private String email;

    @ExcelProperty(value = "姓名加地址")
    @ColumnWidth(35)
    @ApiModelProperty(value = "姓名加地址")
    private String nameEmail;

    @ExcelProperty(value = "电话号码")
    @ApiModelProperty(value = "电话号码")
    @ColumnWidth(15)
    private String phone;

    @ExcelProperty(value = "分组")
    @ApiModelProperty(value = "分组")
    @ColumnWidth(13)
    private String groupId;

    @ExcelProperty(value = "用户id")
    @ApiModelProperty(value = "用户id")
    @ColumnWidth(30)
    private String referUserId;

    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    @ColumnWidth(23)
    private String remark;

    @ExcelProperty(value="0 删除 1未删除")
    @ApiModelProperty(value="0 删除 1未删除")
    @ColumnWidth(10)
    private Integer iFlag;

    public Integer getiFlag() {
        return iFlag;
    }

    public void setiFlag(Integer iFlag) {
        this.iFlag = iFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameEmail() {
        return nameEmail;
    }

    public void setNameEmail(String nameEmail) {
        this.nameEmail = nameEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getReferUserId() {
        return referUserId;
    }

    public void setReferUserId(String referUserId) {
        this.referUserId = referUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Contact{" +
        "id=" + id +
        ", name=" + name +
        ", email=" + email +
        ", nameEmail=" + nameEmail +
        ", phone=" + phone +
        ", groupId=" + groupId +
        ", referUserId=" + referUserId +
        ", remark=" + remark +
        "}";
    }
}
