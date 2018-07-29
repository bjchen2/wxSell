package com.wxsell.repository;

import com.wxsell.domain.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created By Cx On 2018/6/10 0:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository repository;

    @Test
    public void save(){
        ProductCategory productCategory = new ProductCategory("女生最爱",3);
        System.out.println(repository.save(productCategory));
    }

    @Test
    public void findByCategoryType(){
        List<Integer> types = Arrays.asList(2,3,4);
        System.out.println(repository.findByCategoryTypeIn(types));
    }

}