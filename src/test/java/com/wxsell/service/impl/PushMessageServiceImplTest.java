package com.wxsell.service.impl;

import com.wxsell.dto.OrderDto;
import com.wxsell.service.OrderService;
import com.wxsell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/7/30 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    PushMessageService pushMessageService;
    @Autowired
    OrderService orderService;

    @Test
    public void orderFinish() {
        OrderDto orderDto = orderService.findOne("15287976367779230");
        pushMessageService.orderFinish(orderDto);
    }
}