package com.wxsell.handler;

import com.wxsell.config.ProjectConfig;
import com.wxsell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created By Cx On 2018/7/30 12:54
 */
@ControllerAdvice
public class ExceptionHandle {

    @Autowired
    private ProjectConfig projectConfig;

    //拦截登录异常,若检验redis和cookie没有正确的登录值，则跳转回登录界面（微信扫码登录界面）
    @ExceptionHandler(SellerAuthorizeException.class)
    public ModelAndView handleAuthorizeException(){
        //concat()作用等价于+，但concat仅作用于字符串，且比+的效率高。
        //+是将后面的对象重载为字符串，然后再连接成新的对象，所以效率较低，但可以作用于对象
        //TODO 因为没钱办微信认证，所以暂时不跳转到微信扫码登录页面，假装成功扫码跳转到登录界面即可
//        return new ModelAndView("redirect:".concat(projectConfig.getWechatOpenAuthorize())
//                .concat("/sell/wechat/qrAuthorize?returnUrl=").concat(projectConfig.getSell())
//        .concat("/sell/seller/login"));
        return new ModelAndView("redirect:".concat(projectConfig.getSell()).concat("/sell/seller/login?openid=100"));
    }
}
