package com.wxsell.aspect;

import com.wxsell.constant.CookieConstant;
import com.wxsell.constant.RedisConstant;
import com.wxsell.exception.SellException;
import com.wxsell.exception.SellerAuthorizeException;
import com.wxsell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created By Cx On 2018/7/30 12:41
 */
@Aspect
@Slf4j
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //切点范围为com.wxsell.controller包下除SellerUserController的所有Seller*
    @Pointcut("execution(public * com.wxsell.controller..Seller*.*(..)) && " +
            "!execution(public * com.wxsell.controller..SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        //获取HttpHttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie == null){
            //如果cookie不存在
            log.warn("【登陆校验】登录失败，cookie为空");
            throw new SellerAuthorizeException();
        }
        String token = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(token)){
            //如果redis中不存在
            log.warn("【登陆校验】登录失败，redis查询为空");
            throw new SellerAuthorizeException();
        }
    }
}
