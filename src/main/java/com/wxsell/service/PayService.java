package com.wxsell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.wxsell.dto.OrderDto;

/**
 * 支付
 * Created By Cx On 2018/7/23 12:35
 */
public interface PayService {

    /**
     * 发起支付请求
     * @param orderDto
     * @return
     */
    PayResponse create(OrderDto orderDto);

    /**
     * 支付异步通知，当支付成功后该方法会接收到通知
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     */
    RefundResponse refund(OrderDto orderDto);
}
