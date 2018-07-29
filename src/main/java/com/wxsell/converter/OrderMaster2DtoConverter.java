package com.wxsell.converter;

import com.wxsell.domain.OrderMaster;
import com.wxsell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 将OrderMaster类转换为OrderDto类
 * Created By Cx On 2018/6/13 18:41
 */
public class OrderMaster2DtoConverter {

    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMaster){
        return orderMaster.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
