package com.wxsell.service.impl;

import com.wxsell.domain.ProductCategory;
import com.wxsell.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/6/10 14:39
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceImplTest {

    @Autowired
    CategoryService service;

    @Test
    public void getOne() {
        System.out.println(service.getOne(2));
    }

    @Test
    public void findAll() {
        System.out.println(service.findAll());
    }

    @Test
    public void findByCategoryTypeIn() {
        System.out.println(service.findByCategoryTypeIn(Arrays.asList(1,2,3)));
    }

    @Test
    public void save() {
        ProductCategory p = new ProductCategory("男生专享",10);
        System.out.println(service.save(p));
    }
}