package com.wxsell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wxsell.dto.OrderDto;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.service.OrderService;
import com.wxsell.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Cx On 2018/7/23 12:33
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    OrderService orderService;
    @Autowired
    PayService payService;

    @GetMapping("/create")
    public ModelAndView create(String returnUrl, String orderId){
        //查询订单是否存在
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //如果订单存在，则进行支付操作

        Map<String,Object> m = new HashMap<>();
        PayResponse payResponse = payService.create(orderDto);
        m.put("payResponse",payResponse);
        m.put("returnUrl",returnUrl);
        //将m作为参数传到resource/templates/pay/create.ftl页面中，该页面可由：localhost:8080/sell/pay/create 访问
        return new ModelAndView("pay/create",m);
    }

    /**
     * 支付异步通知，当微信确认支付成功后会访问该路由，然后该方法会接收到通知
     * 该路由地址需要与微信配置的 notifyUrl 值相同，因为该链接是通过【下单A】中提交的参数notify_url设置
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //修改完后告诉微信处理结果，不然微信会一直发送异步通知，则该方法会一直被调用
        return new ModelAndView("pay/success");
    }

}
