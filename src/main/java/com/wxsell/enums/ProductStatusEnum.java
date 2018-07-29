package com.wxsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品状态枚举
 * Created By Cx On 2018/6/10 21:41
 */
@AllArgsConstructor
@Getter
public enum ProductStatusEnum {
    UP(0, "在架上"),
    DOWN(1, "下架");
    //状态码
    private Integer code;
    //商品状态描述
    private String message;

}
