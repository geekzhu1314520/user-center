package com.watermelon.medium.usercenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {
    /**
     * 设置微信小程序的appid.
     */
    private String appid;

    /**
     * 设置微信小程序的Secret.
     */
    private String secret;

}
