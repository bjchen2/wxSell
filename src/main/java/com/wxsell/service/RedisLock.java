package com.wxsell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis分布式锁
 * Created By Cx On 2018/7/31 15:41
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key 商品id
     * @param value 过期时间
     * @return
     */
    public boolean lock(String key,String value){
        //setIfAbsent : 如果key存在，返回false，若不存在，设置key-value，并返回true
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        //有可能：上锁后发生异常，导致没有解锁，则系统死锁，所以即使key存在，也要判断是否过期
        //因为可能获取null，所以这里不能直接转换为long
        String currentValue = redisTemplate.opsForValue().get(key);
        //注意，一定要使用Long.parseLong(currentValue)，Long.getLong(currentValue)可能会返回null
        if (!StringUtils.isEmpty(currentValue) && System.currentTimeMillis() > Long.parseLong(currentValue)){
            //如果当前时间大于过期时间，这里不能直接set value，因为可能有多个线程进来
            //getAndSet ： 先返回key对应的value，再把value设置进去
            String temp = redisTemplate.opsForValue().getAndSet(key, value);
            if (currentValue.equals(temp)){
                //只有第一个执行getAndSet方法的才有资格继续执行
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     */
    public void unlock(String key){
        try {
            redisTemplate.opsForValue().getOperations().delete(key);
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常，{}",e);
        }
    }
}
