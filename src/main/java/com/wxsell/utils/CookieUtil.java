package com.wxsell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * Created By Cx On 2018/7/30 10:28
 */
public class CookieUtil {

    /**
     * 设置cookie值
     */
    public static void setCookie(HttpServletResponse response,String key,String value,Integer expire){
        Cookie cookie = new Cookie(key,value);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }

    /**
     * 获取key为name的cookie
     */
    public static Cookie getCookie(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(name)){
                return cookie;
            }
        }
        return null;
    }
}
