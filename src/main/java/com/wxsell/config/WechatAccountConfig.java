package com.wxsell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created By Cx On 2018/7/21 23:18
 */
@Component
@Data
@ConfigurationProperties("wechat")
public class WechatAccountConfig {

    /**
     * 公众号appId
     */
    private String mpAppId;

    /**
     * 公众号appSecret
     */
    private String mpAppSecret;

    /**
     * 开放平台（用于第三方登录——微信扫码登录）appId
     */
    private String openAppId;

    /**
     * 开放平台（用于第三方登录——微信扫码登录）appSecret
     */
    private String openAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

    /**
     * 微信模板ID
     */
    private Map<String,String> templateId;
}
