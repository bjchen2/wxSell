package com.wxsell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目地址配置
 * Created By Cx On 2018/7/29 16:00
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectConfig {

    /**
     * 微信公众平台授权url
     */
    private String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    private String wechatOpenAuthorize;
}
