package com.wxsell.service.impl;

import com.wxsell.domain.ProductCategory;
import com.wxsell.repository.ProductCategoryRepository;
import com.wxsell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created By Cx On 2018/6/10 14:36
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository categoryRepository;

    @Override
    public ProductCategory getOne(Integer id) {
        Optional<ProductCategory> category = categoryRepository.findById(id);
        return category.isPresent() ? category.get() : null;
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> types) {
        return categoryRepository.findByCategoryTypeIn(types);
    }

    @Override
    public ProductCategory findByCategoryType(Integer type) {
        return categoryRepository.findByCategoryType(type);
    }

    @Override
    public ProductCategory save(ProductCategory p) {
        return categoryRepository.save(p);
    }
}
