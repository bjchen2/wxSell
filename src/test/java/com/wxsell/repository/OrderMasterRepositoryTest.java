package com.wxsell.repository;

import com.wxsell.domain.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created By Cx On 2018/6/11 9:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    private final String OPENID = "13qwe";
    private final String ORDERID = "15287976367779230";

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderAmount(new BigDecimal(666.66));
        orderMaster.setOrderId("2");
        orderMaster.setUserAddress("家");
        orderMaster.setUserName("小明");
        orderMaster.setUserOpenid(OPENID);
        orderMaster.setUserPhone("123454");
        repository.save(orderMaster);
    }

    @Test
    public void findByUserOpenid() {
        PageRequest page = PageRequest.of(0,2);
        System.out.println(repository.findByUserOpenid(OPENID,page).getContent());
    }

    @Test
    public void findById() {
        System.out.println(repository.findById("3").isPresent());
        System.out.println(repository.findById("2").isPresent());
    }
}