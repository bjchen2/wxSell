package com.wxsell;

import com.wxsell.config.WechatAccountConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.wxsell.domain.mapper")
@EnableCaching
public class WxsellApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxsellApplication.class, args);
	}
}
