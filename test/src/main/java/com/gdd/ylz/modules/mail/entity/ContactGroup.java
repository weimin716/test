package com.gdd.ylz.modules.mail.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 联系人分组
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@ApiModel(value="ContactGroup对象", description="联系人分组")
public class ContactGroup implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户id")
    private String referUserId;

    @ApiModelProperty(value = "备注")
    private String remark;


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
        return "ContactGroup{" +
        "id=" + id +
        ", name=" + name +
        ", referUserId=" + referUserId +
        ", remark=" + remark +
        "}";
    }
}
