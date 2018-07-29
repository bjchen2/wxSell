package com.wxsell.converter;

import com.wxsell.dto.OrderDto;
import com.wxsell.form.OrderForm;

/**
 * 将OrderForm类转换为OrderDto类
 * Created By Cx On 2018/6/15 11:12
 */
public class OrderForm2DtoConverter {

    public static OrderDto convert(OrderForm orderForm){
        OrderDto orderDto = new OrderDto();
        orderDto.setUserName(orderForm.getName());
        orderDto.setUserOpenid(orderForm.getOpenid());
        orderDto.setUserAddress(orderForm.getAddress());
        orderDto.setUserPhone(orderForm.getPhone());
        orderDto.setOrderDetailList(orderForm.getItems());
        return orderDto;
    }
}
