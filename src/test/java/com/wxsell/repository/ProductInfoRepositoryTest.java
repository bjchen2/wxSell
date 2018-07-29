package com.wxsell.repository;

import com.wxsell.domain.ProductInfo;
import com.wxsell.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/6/10 20:52
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void save(){
        ProductInfo product = new ProductInfo();
        product.setCategoryType(2);
        product.setProductDescription("hello 世界");
        product.setProductIcon("http://www.xxxx.jpg");
        product.setProductId("1");
        product.setProductName("米饭");
        product.setProductPrice(new BigDecimal(2.3));
        product.setProductStatus(ProductStatusEnum.UP.getCode());
        product.setProductStock(20);
        repository.save(product);
    }

    @Test
    public void find(){
        System.out.println( repository.findByProductStatus(ProductStatusEnum.UP.getCode()));
    }

}