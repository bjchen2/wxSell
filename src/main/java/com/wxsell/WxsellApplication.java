package com.wxsell;

import com.wxsell.config.WechatAccountConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WxsellApplication {



	public static void main(String[] args) {
		SpringApplication.run(WxsellApplication.class, args);
	}
}
