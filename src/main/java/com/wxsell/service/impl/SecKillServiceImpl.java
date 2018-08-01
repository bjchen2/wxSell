package com.wxsell.service.impl;


import com.wxsell.exception.SellException;
import com.wxsell.service.RedisLock;
import com.wxsell.service.SecKillService;
import com.wxsell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀有关service（与项目无关，仅学习分布式，高并发使用）
 * Created By Cx On 2018/7/31 14:50
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    //过期时间，1000毫秒
    private static final long TIME = 1*1000;

    @Autowired
    RedisLock redisLock;

    /**
     * 国庆活动，商品特价，限量100000份
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         * stock和products key为商品id，products的value为商品总量，stock为剩余量
         * orders key为买家id，value为商品id
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId) {
        return "国庆活动，商品特价，限量份"
                + products.get(productId)
                + " 还剩：" + stock.get(productId) + " 份"
                + " 该商品成功下单用户数目："
                + orders.size() + " 人";
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {

        long value = System.currentTimeMillis() + TIME;
        //加锁
        if (!redisLock.lock(productId,String.valueOf(value))){
            //如果加锁失败
            throw new SellException("请求失败，当前访问人数过多，请稍后重试",101);
        }
        //1.查询该商品库存，为0则活动结束。
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException("活动结束", 100);
        } else {
            //2.下单(模拟不同用户openid不同)
            orders.put(KeyUtil.genUniqueKey(), productId);
            //3.减库存
            stockNum = stockNum - 1;
            try {
                //线程休眠，避免网络延时，io延时等，相当于线程等着这些都操作完再继续操作
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }
        //解锁
        redisLock.unlock(productId);
    }
}
