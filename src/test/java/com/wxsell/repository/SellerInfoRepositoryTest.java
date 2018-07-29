package com.wxsell.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/7/27 19:09
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Test
    public void getByOpenid() {
        System.out.println(sellerInfoRepository.getByOpenid("120"));
        System.out.println("========================================");
    }

    @Test
    public void getByUsernameAndPassword() {
        System.out.println(sellerInfoRepository.getByUsernameAndPassword("test","100"));
    }
}