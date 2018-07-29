package com.wxsell.controller;

import com.wxsell.config.ProjectConfig;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信有关路由
 * 请求获取openid流程：
 * ①带上returnUrl（该url为获取openid后返回的地址）访问authorize方法的路径（该路径由前端自己设置），
 * ②通过oauth2buildAuthorizationUrl方法设置url，向微信端发送请求获取code
 * ③获取code后，微信返回一个url，即原设置url加上code参数，然后authorize方法跳转到该url
 * ④访问userInfo方法，通过oauth2getAccessToken方法，用code换取openid，获取openid
 * ⑤带上openid，重定向到一开始传来的returnUrl
 * Created By Cx On 2018/6/16 13:47
 */
@RequestMapping("/wechat")
@Slf4j
@Controller
public class WechatController {
    //使用轮子，配置已在config包中完成
    //特别注意：因为在wechatOpen/MpConfig中分别定义了一个WxMpService，所以WxMpService相当于有两个bean，
    // 根据@Autowired注解特性：默认先按byType，如果发现找到多个bean，则又按照byName方式比对，如果有多个或找不到，则报出异常
    // 所以这里的变量名固定只能为该名字，不能更改
    @Autowired
    WxMpService wxMpService;
    @Autowired
    WxMpService wxOpenService;
    @Autowired
    ProjectConfig projectConfig;

    //获取code，并跳转到userInfo方法
    @GetMapping("/authorize")
    public String authorize(String returnUrl) throws UnsupportedEncodingException {
        String url = projectConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"UTF-8"));
        return "redirect:" + redirectUrl;
    }

    //通过code获取openid，带着openid跳转回returnUrl
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("state") String returnUrl, String code){
        //获得access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权]{}", e);
            throw new SellException(e.getError().getErrorMsg(),ResultEnum.WECHAT_MP_ERROR.getCode());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        returnUrl += "?openid="+openId;
        return "redirect:" + returnUrl;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(String returnUrl) throws UnsupportedEncodingException {
        String url = projectConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl,"utf-8"));
        return "redirect:"+ redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("state") String returnUrl,String code){
        //获得access token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信后台网页授权]{}", e);
            throw new SellException(e.getError().getErrorMsg(),ResultEnum.WECHAT_MP_ERROR.getCode());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        returnUrl += "?openid="+openId;
        return "redirect:" + returnUrl;
    }




    //手工获取openId 和 access_token ，仅了解，不使用，直接使用 github轮子 更方便
    //当微信号访问该网址时，则会提示是否授权，同意后跳转到/auth方法（详情见微信官方文档：用户同意授权，获取code）
    //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbbf3033f8ef8b911&redirect_uri=http://cx.s1.natapp.cc/sell/wechat/auth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
//    @GetMapping("/auth")
//    public void auth(String code){
//        private final String APPID = "wxbbf3033f8ef8b911",SECRET="dd98d7713642ae3725341cceca6f58e1";
//        log.info("进入auth方法");
//        log.info("code={}",code);
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
//        System.out.println(url);
//        RestTemplate restTemplate = new RestTemplate();
//        log.info("response={}",restTemplate.getForObject(url,String.class));
//    }

}
