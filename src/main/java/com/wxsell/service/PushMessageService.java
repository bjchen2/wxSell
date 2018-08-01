package com.wxsell.service;

import com.wxsell.dto.OrderDto;

/**
 * 消息推送service
 * Created By Cx On 2018/7/30 20:17
 */
public interface PushMessageService {

    /**
     * 订单已接单推送
     */
    void orderFinish(OrderDto orderDto);
}
