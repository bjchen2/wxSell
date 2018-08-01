package com.wxsell.service.impl;

import com.wxsell.domain.ProductInfo;
import com.wxsell.dto.CartDto;
import com.wxsell.enums.ProductStatusEnum;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.repository.ProductInfoRepository;
import com.wxsell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created By Cx On 2018/6/10 21:39
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "product")
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    @Cacheable(key = "#id",unless = "#result == null ")
    public ProductInfo findOne(String id) {
        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);
        //isPresent方法,判断返回的Optional是否为有价值的值（即是否不为空），若不为空则为true，否则false
        if (productInfo.isPresent()){
            return productInfo.get();
        }
        return null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    @CachePut(key = "#product.productId")
    public ProductInfo save(ProductInfo product) {
        return productInfoRepository.save(product);
    }

    @Override
    public void increaseStock(List<CartDto> cartDtoList) {
        List<ProductInfo> changeProducts = new ArrayList<>();
        for (CartDto cartDto : cartDtoList){
            ProductInfo product = findOne(cartDto.getProductId());
            if (product == null){
                //如果商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //增库存
            int stock = product.getProductStock() + cartDto.getProductQuantity();
            product.setProductStock(stock);
            changeProducts.add(product);
        }
        productInfoRepository.saveAll(changeProducts);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        List<ProductInfo> changeProducts = new ArrayList<>();
        for (CartDto cartDto : cartDtoList){
            ProductInfo product = findOne(cartDto.getProductId());
            if (product == null){
                //如果商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //减库存
            int stock = product.getProductStock() - cartDto.getProductQuantity();
            if (stock < 0){
                //库存不足
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            product.setProductStock(stock);
            changeProducts.add(product);
        }
        productInfoRepository.saveAll(changeProducts);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            //商品已上架
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoRepository.save(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            //商品已下架
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoRepository.save(productInfo);
        return productInfo;
    }
}
