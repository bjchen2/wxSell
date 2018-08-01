package com.wxsell.service.impl;

import com.wxsell.domain.OrderDetail;
import com.wxsell.domain.ProductInfo;
import com.wxsell.dto.OrderDto;
import com.wxsell.service.OrderService;
import com.wxsell.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/6/12 17:29
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    private final String OPENID = "13qwe";
    private final String ORDERID = "15287976367779230";

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserName("小红帽");
        orderDto.setUserPhone("01234567");
        orderDto.setUserAddress("我价的位");
        orderDto.setUserOpenid(OPENID);
        List<OrderDetail> details = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(3);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);
        details.add(o1); details.add(o2);
        orderDto.setOrderDetailList(details);
        System.out.println(JsonUtil.toJson(orderDto));;
        //System.out.println(orderService.create(orderDto));
    }

    @Test
    public void findOne() {
        System.out.println( orderService.findOne(ORDERID));
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        System.out.println(orderService.findList("123",pageRequest).getTotalPages());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne(ORDERID);
        System.out.println(orderService.cancel(orderDto));
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}