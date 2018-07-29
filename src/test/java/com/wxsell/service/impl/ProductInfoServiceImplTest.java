package com.wxsell.service.impl;

import com.wxsell.domain.ProductInfo;
import com.wxsell.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/6/10 22:08
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductInfoServiceImplTest {

    @Autowired
    ProductInfoServiceImpl service;

    @Test
    public void findOne() {
        System.out.println(service.findOne("1"));
        System.out.println(service.findOne("41897"));
    }

    @Test
    public void findUpAll() {
        System.out.println(service.findUpAll());
    }

    @Test
    public void findAll() {
        PageRequest pageable = PageRequest.of(0,2);
        System.out.println(service.findAll(pageable).getTotalElements());
    }

    @Test
    @Rollback
    public void save() {
        ProductInfo product = new ProductInfo();
        product.setCategoryType(2);
        product.setProductDescription("he无54所事事 世界");
        product.setProductIcon("http://www.scg.jpg");
        product.setProductId("65");
        product.setProductName("汤w");
        product.setProductPrice(new BigDecimal(1.116));
        product.setProductStatus(ProductStatusEnum.DOWN.getCode());
        product.setProductStock(30);
        System.out.println(service.save(product));
    }
}