package com.gdd.ylz.modules.mail.service;

import com.gdd.ylz.modules.mail.dto.MsgQueryDTO;
import com.gdd.ylz.modules.mail.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.mail.dto.EmailMsgDTO;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 邮件信息 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
public interface IMsgService extends IService<Msg> {
    /**
     * 发送文本邮件
     * */
    boolean sendSimpleMail(EmailMsgDTO msg,TPlattUser plattUser);

    /**
     * 发送HTML邮件
     * */
    DataResult sendHtmlMail(EmailMsgDTO msg, TPlattUser plattUser);

    DataResult getAlreadySendCount(String userId);

    DataResult getSendEmailList(MsgQueryDTO msgQueryDTO, String userId);

    DataResult getHtmlMails(TPlattUser plattUser);

    DataResult getRecieveEmailList(MsgQueryDTO msgQueryDTO, TPlattUser plattUser);
}
