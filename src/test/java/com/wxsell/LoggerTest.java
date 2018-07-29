package com.wxsell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created By Cx On 2018/6/4 10:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {


    @Test
    public void test1(){
        int a = 3;
        log.debug("hello bug!!!");
        log.info("a = {}",a);
        log.error("good error");
        log.warn("bad warn");
    }

}
