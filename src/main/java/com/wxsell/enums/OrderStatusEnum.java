package com.wxsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 * Created By Cx On 2018/6/11 9:24
 */
//继承CodeEnum，保证有getCode方法，使该类能够通过EnumUtil类遍历查询code值符合要求的枚举
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新下单"),
    FINISHED(1, "已结单"),
    CANCEL(2, "已取消");

    //状态码
    private Integer code;
    //状态描述
    private String message;

}
