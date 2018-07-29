package com.wxsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态枚举
 * Created By Cx On 2018/6/11 9:26
 */
//继承CodeEnum，保证有getCode方法，使该类能够通过EnumUtil类遍历查询code值符合要求的枚举
@Getter
@AllArgsConstructor
public enum PayStatusEnum implements CodeEnum{

    WAIT(0, "未支付"),
    SUCCESS(1, "支付成功");
    //状态码
    private Integer code;
    //状态描述
    private String message;
}
