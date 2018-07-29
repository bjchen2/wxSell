package com.wxsell.repository;

import com.wxsell.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created By Cx On 2018/6/10 0:31
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> types);
    ProductCategory findByCategoryType(Integer type);
}
