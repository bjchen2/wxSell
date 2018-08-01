package com.wxsell.service.impl;

import com.wxsell.dto.OrderDto;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.service.BuyerService;
import com.wxsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/6/15 19:29
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderService orderService;

    /**
     * 判断订单是否属于该用户，若订单号不存在，则返回null，若不属于该用户，则抛异常
     */
    public OrderDto checkOrderOwner(String openid, String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            return null;
        }
        //判断该订单是否属于用户
        if (!orderDto.getUserOpenid().equals(openid)){
            //openid不匹配
            log.error("[查询订单]openid不相同，openid={},orderDto={}",openid,orderDto);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid,orderId);
        if (orderDto == null){
            log.error("[取消订单]无法取消订单，订单不存在，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDto);
    }
}
