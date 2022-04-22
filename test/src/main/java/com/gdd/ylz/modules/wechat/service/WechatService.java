package com.gdd.ylz.modules.wechat.service;

import com.gdd.ylz.modules.wechat.dto.WechatLoginDTO;
import com.gdd.ylz.result.DataResult;

public interface WechatService {
    String getQcodeUrl();

    DataResult doCallback(String code, String state);

    DataResult getAndSaveUserInfo(WechatLoginDTO wechatLoginDTO);
}
