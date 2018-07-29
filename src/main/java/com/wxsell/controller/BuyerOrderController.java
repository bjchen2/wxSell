package com.wxsell.controller;

import com.wxsell.VO.ResultVO;
import com.wxsell.converter.OrderForm2DtoConverter;
import com.wxsell.dto.OrderDto;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.form.OrderForm;
import com.wxsell.service.BuyerService;
import com.wxsell.service.OrderService;
import com.wxsell.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 买家端订单有关接口
 * Created By Cx On 2018/6/14 22:13
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    BuyerService buyerService;

    //创建订单
    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            //表单校验有误
            log.error("[创建订单]参数不正确，orderForm={}",orderForm);
            throw new SellException(bindingResult.getFieldError().getDefaultMessage(),ResultEnum.PARAM_ERROR.getCode());
        }
        OrderDto orderDto = OrderForm2DtoConverter.convert(orderForm);
        orderDto = orderService.create(orderDto);
        Map<String,String> result = new HashMap<>();
        result.put("orderId",orderDto.getOrderId());
        return ResultUtil.success(result);
    }

    //查询订单概要列表,如果不传page和size参数，则默认按一页10条数据，返回第一页
    @GetMapping(value = "/list")
    public ResultVO findList(String openid, @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer size){
        if (openid.isEmpty()){
            log.error("[查询订单列表]openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid,pageRequest);
        return ResultUtil.success(orderDtoPage.getContent());
    }

    //查询某个用户的某个订单，包括订单详情
    @GetMapping("/detail")
    public ResultVO detail(String openid, String orderId){
        return ResultUtil.success(buyerService.findOrderOne(openid,orderId));
    }

    //修改某个订单的订单状态为已取消
    @PostMapping("/cancel")
    public ResultVO cancel(String openid, String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultUtil.success();
    }
}
