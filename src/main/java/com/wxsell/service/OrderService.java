package com.wxsell.service;

import com.wxsell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created By Cx On 2018/6/11 10:58
 */
public interface OrderService {

    /**
     * 创建订单概要
     *
     * @return
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 通过订单id查询某个订单概要(包含订单详情，即商品信息)
     *
     * @return
     */
    OrderDto findOne(String orderId);

    /**
     * 查询某个用户的所有订单概要，分页返回(不包含订单详情，即商品信息)
     *
     * @return
     */
    Page<OrderDto> findList(String openId, Pageable pageable);

    /**
     * 查询所有用户的所有订单概要，分页返回(不包含订单详情，即商品信息)
     */
    Page<OrderDto> findList(Pageable pageable);

    /**
     * 取消订单
     *
     * @return
     */
    OrderDto cancel(OrderDto orderDto);

    /**
     * 完成订单（即已接单）
     *
     * @return
     */
    OrderDto finish(OrderDto orderDto);

    /**
     * 支付订单
     *
     * @return
     */
    OrderDto paid(OrderDto orderDto);


}
