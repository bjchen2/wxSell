package com.wxsell.service;

/**
 * 秒杀有关service（与项目无关，仅学习使用）
 * Created By Cx On 2018/7/31 14:50
 */
public interface SecKillService {

    /**
     * 查询秒杀活动特价商品的信息
     *
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * 模拟不同用户秒杀同一商品的请求
     *
     * @param productId
     * @return
     */
    void orderProductMockDiffUser(String productId);

}
