package com.wxsell.service;

import com.wxsell.domain.SellerInfo;

/**
 * 卖家端service
 * Created By Cx On 2018/7/27 19:23
 */
public interface SellerService {
    SellerInfo findSellerInfoByOpenId(String openId);
}
