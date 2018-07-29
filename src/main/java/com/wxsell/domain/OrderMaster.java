package com.wxsell.domain;

import com.wxsell.enums.OrderStatusEnum;
import com.wxsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单概要表
 * Created By Cx On 2018/6/10 15:00
 */
//@DynamicUpdate注解，使update字段自动更新
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class OrderMaster {

    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态，默认0，未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //订单总额
    private BigDecimal orderAmount;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
