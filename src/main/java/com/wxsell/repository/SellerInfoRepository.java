package com.wxsell.repository;

import com.wxsell.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By Cx On 2018/7/27 18:53
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String>{
    //findBy 等价于 getBy
    SellerInfo getByOpenid(String openid);
    SellerInfo getByUsernameAndPassword(String username,String password);
}
