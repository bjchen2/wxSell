package com.wxsell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wxsell.dto.OrderDto;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.service.OrderService;
import com.wxsell.service.PayService;
import com.wxsell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created By Cx On 2018/7/23 12:45
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    BestPayService bestPayService;
    @Autowired
    OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOpenid(orderDto.getUserOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，request={}",payRequest);
        //TODO 无法成功，因为配置文件中没有设置mchId、mchKey、keyPath
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，response={}",payResponse);
        return payResponse;
    }

    /**
     * 处理异步通知注意事项：
     *  1.验证签名是否正确。防止他人模拟发送一个异步请求
     *  2.验证支付状态是否为支付成功
     *  3.验证支付金额是否正确
     *  4.验证支付人（支付人 == 下单人）   按需验证，不需要可以不验证，其他都必须验证
     *
     *  1、2 bestPay SDK 已经验证。
     */
    @Override
    public PayResponse notify(String notifyData) {
        //处理异步通知
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付]，异步通知，payResponse={}",payResponse);

        //查询订单，并判断订单是否存在，金额是否一致
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        if(orderDto == null){
            log.error("[微信支付]，异步通知，订单不存在,orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //这种方法是错误的，bigDecimal类不能直接和Double类比较，会有精度缺失，结果必然为false
        //orderDto.getOrderAmount().equals(payResponse.getOrderAmount())
        //正确方法： A和B相减（A、B为double或者bigDecimal均可），若小于某个特别小的值，则说明他们是相等的
        if (!MathUtil.equals(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("[微信支付]，异步通知，订单金额不一致,orderId={},订单金额={},异步通知金额={}",payResponse.getOrderId(),
                    orderDto.getOrderAmount(),payResponse.getOrderAmount());
            throw new SellException(ResultEnum.WECHAT_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDto);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDto orderDto) {
        //设置退款请求
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】发起退款，request={}",refundRequest);
        //退款
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】发起退款，response={}",refundResponse);
        return refundResponse;
    }
}
