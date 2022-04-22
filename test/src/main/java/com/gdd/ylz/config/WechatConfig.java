package com.gdd.ylz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WechatConfig {
    private String appId;

    private String appSecret;

    private String redirectUrl;
}
