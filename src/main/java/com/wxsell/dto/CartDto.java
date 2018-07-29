package com.wxsell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 购物车
 * Created By Cx On 2018/6/11 21:16
 */
@Data
@AllArgsConstructor
public class CartDto {
    //商品Id
    private String productId;
    //商品购买数量
    private Integer productQuantity;

    public CartDto() {
    }
}
