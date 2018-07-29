package com.wxsell.repository;

import com.wxsell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By Cx On 2018/6/11 9:38
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    /**
     * 分页查找某个用户的订单概要
     *
     * @return
     */
    Page<OrderMaster> findByUserOpenid(String openid, Pageable pageable);
}
