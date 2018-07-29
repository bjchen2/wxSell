package com.wxsell.service.impl;

import com.wxsell.domain.SellerInfo;
import com.wxsell.repository.SellerInfoRepository;
import com.wxsell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/7/27 19:24
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SellerServiceImpl implements SellerService{

    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenId(String openId) {
        return sellerInfoRepository.getByOpenid(openId);
    }
}
