package com.wxsell.controller;

import com.wxsell.dto.OrderDto;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Cx On 2018/7/25 13:01
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    OrderService orderService;

    /**
     * 查询所有订单
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "10")Integer size){
        if (page < 1) page = 1;
        Map<String,Object> m = new HashMap<>();
        Pageable pageable = PageRequest.of(page - 1,size);
        Page<OrderDto> orderDtoPage = orderService.findList(pageable);
        m.put("orderDtoPage",orderDtoPage);
        m.put("currentPage",page);
        m.put("size",size);
        return new ModelAndView("/order/list",m);
    }

    /**
     * 取消订单
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(String orderId){
        Map<String,Object> m = new HashMap<>();
        m.put("url","/sell/seller/order/list");
        try{
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
        }catch (SellException e){
            //如果订单不存在
            log.error("【卖家端取消订单】发生异常{}",e);
            m.put("msg", e.getMessage());
            return new ModelAndView("common/error",m);
        }
        m.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        return new ModelAndView("common/success",m);
    }

    /**
     * 商品详情
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(String orderId){
        Map<String,Object> m = new HashMap<>();
        OrderDto orderDto;
        try{
            orderDto = orderService.findOne(orderId);
        }catch (SellException e){
            //如果订单不存在
            log.error("【卖家端查询订单详情】发生异常{}",e);
            m.put("msg", e.getMessage());
            m.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",m);
        }
        m.put("orderDto",orderDto);
        return new ModelAndView("order/detail",m);
    }

    /**
     * 完结订单
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(String orderId){
        Map<String,Object> m = new HashMap<>();
        try{
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.finish(orderDto);
        }catch (SellException e){
            //如果订单不存在
            log.error("【卖家端完结订单】发生异常{}",e);
            m.put("msg", e.getMessage());
            m.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",m);
        }
        m.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        return new ModelAndView("common/success",m);
    }
}
