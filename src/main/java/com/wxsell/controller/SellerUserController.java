package com.wxsell.controller;

import com.wxsell.config.ProjectConfig;
import com.wxsell.constant.CookieConstant;
import com.wxsell.constant.RedisConstant;
import com.wxsell.domain.SellerInfo;
import com.wxsell.enums.ResultEnum;
import com.wxsell.service.SellerService;
import com.wxsell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户有关
 * Created By Cx On 2018/7/30 9:45
 */
@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    SellerService sellerService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProjectConfig projectConfig;


    @GetMapping("/login")
    public ModelAndView login(String openid, HttpServletResponse response){
        Map<String,Object> m = new HashMap<>();
        m.put("url","/sell/seller/order/list");
        //1.在数据库中查询该用户是否存在
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openid);
        if (sellerInfo == null){
            m.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            return new ModelAndView("common/error",m);
        }

        //2.设置token 到 redis
        //生成随机数，官方解释：使用加密的强伪随机数生成器生成该 UUID
        String token = UUID.randomUUID().toString();
        //过期时间，expire:期满
        Integer expire = RedisConstant.EXPIRE;
        //opsForValue表示对某个值进行操作，set参数：key、value、过期时间、时间单位
        //String.format(RedisConstant.TOKEN_PREFIX,token):将token按前者的格式，格式化
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3.设置token 到 cookie
        CookieUtil.setCookie(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        return new ModelAndView("redirect:"+ projectConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> m = new HashMap<>();
        //获取cookie
        Cookie cookie = CookieUtil.getCookie(request,CookieConstant.TOKEN);
        if (cookie != null){
            //若cookie存在，清除redis和cookie
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            CookieUtil.setCookie(response,CookieConstant.TOKEN,null,0);
        }
        m.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
        m.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",m);
    }
}
