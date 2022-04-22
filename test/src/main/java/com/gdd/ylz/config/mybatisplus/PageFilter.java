package com.gdd.ylz.config.mybatisplus;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Administrator
 */
public class PageFilter {
    @ApiModelProperty(value = "关键字查询", example = "关键字", position = 0)
    private String keyWord;
    @ApiModelProperty(value = "第几页 默认1开始", example = "1", position = 1)
    private int start;
    @ApiModelProperty(value = "每页大小", example = "10", position = 2)
    private int length;
    @ApiModelProperty(value = "排序", example = "", position = 3)
    private String order;
    @ApiModelProperty(value = "登陆用户所属机构id",  position = 4)
    private String userOrganId;
    @ApiModelProperty(value = "数据边界，1有0无",  position = 5)
    private Integer dataLimit;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(Integer dataLimit) {
        this.dataLimit = dataLimit;
    }

    @Override
    public String toString() {
        return "PageFilter{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
