package com.wxsell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息实体类
 * Created By Cx On 2018/7/27 18:49
 */
@Data
@Entity
public class SellerInfo {
    //卖家id
    @Id
    private String sellerId;
    //卖家用户名
    private String username;
    //卖家密码
    private String password;
    //卖家微信openid
    private String openid;
}
