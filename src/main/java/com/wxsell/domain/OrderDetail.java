package com.wxsell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情表
 * Created By Cx On 2018/6/10 15:06
 */
@Data
@Entity
public class OrderDetail {

    //订单详情id，使用string，使用KeyUtil手动生成Id，防止多线程冲突，模拟大型项目
    @Id
    private String detailId;
    //订单id
    private String orderId;
    //商品id
    private String productId;
    //商品名称
    private String productName;
    //商品单价
    private BigDecimal productPrice;
    //商品数量
    private Integer productQuantity;
    //商品图片url
    private String productIcon;
}
