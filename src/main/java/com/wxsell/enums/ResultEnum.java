package com.wxsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息集
 * Created By Cx On 2018/6/11 12:24
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAILED(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单商品为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    ORDER_OWNER_ERROR(18,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(19,"微信公众号授权出错"),
    WECHAT_NOTIFY_MONEY_VERIFY_ERROR(20,"微信异步通知金额校验不通过"),
    ORDER_CANCEL_SUCCESS(21,"订单取消成功"),
    ORDER_FINISH_SUCCESS(22,"订单完结成功"),
    PRODUCT_STATUS_ERROR(23,"订单状态不正确"),
    CATEGORY_NOT_EXIST(24,"类目不存在")
    ;

    private Integer code;
    private String msg;
}
