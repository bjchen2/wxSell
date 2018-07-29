package com.wxsell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * wxMpService配置类
 * Created By Cx On 2018/7/21 23:33
 */
@Component
public class WechatMpConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxMpService(){
        //设置配置
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());

        WxMpService wxMpService = new WxMpServiceImpl();
        //配置WxMpService
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

}
