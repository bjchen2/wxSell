package com.wxsell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * wxOpenService配置类
 * Created By Cx On 2018/7/28 0:27
 */
@Component
public class WechatOpenConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Bean
    WxMpService wxOpenService(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        wxMpConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

}
