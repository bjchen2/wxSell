package com.wxsell.form;

import com.wxsell.domain.OrderDetail;
import com.wxsell.dto.CartDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 订单表单参数验证类
 * Created By Cx On 2018/6/15 10:36
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 买家电话
     */
    @NotBlank(message = "电话不能为空")
    private String phone;

    /**
     * 买家地址
     */
    @NotBlank(message = "地址不能为空")
    private String address;

    /**
     * 买家微信openid
     */
    @NotBlank(message = "微信openid不能为空")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车商品不能为空")
    private List<OrderDetail> items;

}
