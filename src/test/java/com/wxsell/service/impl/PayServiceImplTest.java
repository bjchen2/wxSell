package com.wxsell.service.impl;

import com.wxsell.dto.OrderDto;
import com.wxsell.service.OrderService;
import com.wxsell.service.PayService;
import com.wxsell.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/7/23 13:46
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PayServiceImplTest {

    @Autowired
    OrderService orderService;
    @Autowired
    PayService payService;

    @Test
    public void create() {
        //TODO 无法成功，因为配置文件中没有设置mchId、mchKey、keyPath
        OrderDto orderDto = orderService.findOne("15287976367779230");
        payService.create(orderDto);
    }
}