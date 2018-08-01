package com.wxsell.service.impl;

import com.wxsell.converter.OrderMaster2DtoConverter;
import com.wxsell.domain.OrderDetail;
import com.wxsell.domain.OrderMaster;
import com.wxsell.domain.ProductInfo;
import com.wxsell.dto.CartDto;
import com.wxsell.dto.OrderDto;
import com.wxsell.enums.OrderStatusEnum;
import com.wxsell.enums.PayStatusEnum;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.repository.OrderDetailRepository;
import com.wxsell.repository.OrderMasterRepository;
import com.wxsell.service.*;
import com.wxsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanMetadataAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created By Cx On 2018/6/11 12:01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    PayService payService;
    @Autowired
    PushMessageService pushMessageService;
    @Autowired
    WebSocket webSocket;

    @Override
    public OrderDto create(OrderDto orderDto) {
        //随机生成订单Id
        String orderId = KeyUtil.genUniqueKey();
        //将每件商品的购买数量和Id加入购物车列表;lambda表达式，更简便
        List<CartDto> cartDtos = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        //订单总价
        BigDecimal amount = new BigDecimal(0);
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            //查询商品（数量，价格）
            ProductInfo p = productInfoService.findOne(orderDetail.getProductId());
            if (p == null) {
                //若商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //将orderDetail信息补全
            BeanUtils.copyProperties(p, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            //加上每件商品总价
            amount = amount.add(orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
        }
        //将订单详情添加到数据库
        orderDetailRepository.saveAll(orderDto.getOrderDetailList());
        //将订单概要添加到数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(amount);
        orderMasterRepository.save(orderMaster);
        //扣库存
        productInfoService.decreaseStock(cartDtos);
        //webSocket发送消息，告知卖家有新订单
        webSocket.sendMessage(orderId);
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        Optional<OrderMaster> orderMaster = orderMasterRepository.findById(orderId);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (!orderMaster.isPresent()){
            //若订单概要不存在
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (orderDetails == null){
            //若订单详情不存在
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDto orderDto = OrderMaster2DtoConverter.convert(orderMaster.get());
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String openId, Pageable pageable) {
        //因为订单列表不需要知道订单商品是什么，所以不用设置orderDetail
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByUserOpenid(openId,pageable);
        return new PageImpl<OrderDto>(OrderMaster2DtoConverter.convert(orderMasterPage.getContent()),
                pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        return new PageImpl<OrderDto>(OrderMaster2DtoConverter.convert(orderMasterPage.getContent()),pageable,
                orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态是否可取消(只有新下单状态可取消订单)
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ){
            log.error("[取消订单]订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态为取消订单并保存
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMasterRepository.save(orderMaster);
        //将取消订单的商品返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            //如果商品详情为空
            log.error("[取消订单]该订单无商品信息，orderDto = {}",orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtos = orderDto.getOrderDetailList().stream().map(e -> new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtos);
        //若已支付，则退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDto);
        }
        return orderDto;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getPayStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[接订单]订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态并存储
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMasterRepository.save(orderMaster);
        //推送已接单消息
        pushMessageService.orderFinish(orderDto);
        return orderDto;
    }

    @Override
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getPayStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付]订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付]订单支付状态不正确，orderId={},payStatus={}",orderDto.getOrderId(),orderDto.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //TODO 付款
        //修改订单支付状态并存储
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMasterRepository.save(orderMaster);
        return orderDto;
    }
}
