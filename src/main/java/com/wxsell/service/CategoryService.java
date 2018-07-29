package com.wxsell.service;

import com.wxsell.domain.ProductCategory;

import java.util.List;

/**
 * Created By Cx On 2018/6/10 14:32
 */
public interface CategoryService {

    /**
     * 根据id查询类目信息
     *
     * @return
     */
    ProductCategory getOne(Integer id);

    /**
     * 获取所有类目信息
     *
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 通过指定类目编号范围，获取符合要求的所有类目
     *
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> types);

    /**
     * 通过指定类目编号，获取符合要求的类目
     *
     * @return
     */
    ProductCategory findByCategoryType(Integer type);

    /**
     * 保存该类目
     *
     * @return
     */
    ProductCategory save(ProductCategory p);
}
