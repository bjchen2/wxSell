package com.wxsell.service;

import com.wxsell.dto.OrderDto;

/**
 * 买家端
 * Created By Cx On 2018/6/15 19:24
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @return
     */
    OrderDto findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     * @return
     */
    OrderDto cancelOrder(String openid, String orderId);
}
