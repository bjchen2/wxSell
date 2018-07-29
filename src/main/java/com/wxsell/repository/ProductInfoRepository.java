package com.wxsell.repository;

import com.wxsell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By Cx On 2018/6/10 20:50
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    //查询某状态的所有商品
    List<ProductInfo> findByProductStatus(Integer status);
}
