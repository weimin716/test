package com.gdd.ylz.common.util;

import com.gdd.ylz.config.jwt.JwtTokenUtil;
import com.gdd.ylz.config.jwt.TokenSetting;
import org.springframework.stereotype.Component;

/**
 * @ClassName: InitializerUtil
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Component
public class InitializerUtil {

    public InitializerUtil(TokenSetting tokenSetting) {
        JwtTokenUtil.setJwtProperties(tokenSetting);
    }
}
