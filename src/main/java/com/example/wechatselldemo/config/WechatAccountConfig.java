package com.example.wechatselldemo.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Getter
public class WechatAccountConfig {
    private String mpAppId;
    private String mpAppSecrete;
}
