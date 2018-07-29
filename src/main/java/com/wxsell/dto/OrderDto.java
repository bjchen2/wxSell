package com.wxsell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wxsell.domain.OrderDetail;
import com.wxsell.enums.OrderStatusEnum;
import com.wxsell.enums.PayStatusEnum;
import com.wxsell.utils.EnumUtil;
import com.wxsell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created By Cx On 2018/6/11 11:00
 */
@Data
public class OrderDto {
    //订单id，使用string，使用KeyUtil手动生成Id，防止多线程冲突，模拟大型项目
    private String orderId;
    //买家手机号
    private String userPhone;
    //买家姓名
    private String userName;
    //买家地址
    private String userAddress;
    //买家微信openId
    private String userOpenid;
    //订单状态，默认0，新下单
    private Integer orderStatus;
    //支付状态，默认0，未支付
    private Integer payStatus;
    //订单总额
    private BigDecimal orderAmount;
    //创建时间，@JsonSerialize使其生成json时自动按Date2LongSerializer类要求进行转换
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    //该订单概要对应的订单详情，一对多关系，@JsonInclude当该字段值为null时，生成json不包含该字段
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<OrderDetail> orderDetailList;

    //使用@JsonIgnore注解，使其形成json的时候忽略该方法
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
