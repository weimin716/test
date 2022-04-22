package com.gdd.ylz.modules.mail.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.mail.dto.EmailResultDTO;
import com.gdd.ylz.modules.mail.dto.MsgQueryDTO;
import com.gdd.ylz.modules.mail.entity.Msg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邮件信息 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
public interface MsgMapper extends BaseMapper<Msg> {

    IPage<EmailResultDTO> getSendEmailList(Page page, @Param("msg") MsgQueryDTO msgQueryDTO);

    IPage<EmailResultDTO> getRecieveEmailList(Page page, @Param("msg") MsgQueryDTO msgQueryDTO);
}
