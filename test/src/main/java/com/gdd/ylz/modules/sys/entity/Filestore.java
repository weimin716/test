package com.gdd.ylz.modules.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzg
 * @since 2021-10-12
 */
@ApiModel(value="Filestore对象", description="")
@Data
public class Filestore implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "文件编号同上")
    private String fileId;

    @ApiModelProperty(value = "原始名称")
    private String originalName;

    @ApiModelProperty(value = "新文件名称")
    private String newName;

    @ApiModelProperty(value = "文件后缀")
    private String fileSuffix;

    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @ApiModelProperty(value = "文件存储相对路径")
    private String relativePath;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "是否是图片")
    private Integer isImg;

    @ApiModelProperty(value = "下载次数")
    private BigDecimal downCounts;

    @ApiModelProperty(value = "是否是图像")
    private Integer isAvatar;

}
