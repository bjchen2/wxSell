package com.wxsell.repository;

import com.wxsell.domain.OrderDetail;
import com.wxsell.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/6/11 9:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductInfoService productInfoService;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("3");
        orderDetail.setOrderId("1");
        orderDetail.setProductQuantity(2);
        BeanUtils.copyProperties(productInfoService.findOne("1"),orderDetail);
        orderDetailRepository.save(orderDetail);
    }

    @Test
    public void findByOrderId() {
        System.out.println(orderDetailRepository.findByOrderId("1"));
    }
}