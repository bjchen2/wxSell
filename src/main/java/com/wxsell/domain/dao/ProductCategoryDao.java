package com.wxsell.domain.dao;

import com.wxsell.domain.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 通过mybatis方式对数据库进行操作
 * Created By Cx On 2018/7/31 13:31
 */
public class ProductCategoryDao {

    @Autowired
    ProductCategoryMapper categoryMapper;

    int insertByMap(Map<String,Object> m){
        return categoryMapper.insertByMap(m);
    }
}
