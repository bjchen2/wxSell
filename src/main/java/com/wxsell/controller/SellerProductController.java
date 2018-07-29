package com.wxsell.controller;

import com.wxsell.domain.ProductCategory;
import com.wxsell.domain.ProductInfo;
import com.wxsell.exception.SellException;
import com.wxsell.form.ProductForm;
import com.wxsell.service.CategoryService;
import com.wxsell.service.ProductInfoService;
import com.wxsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * Created By Cx On 2018/7/26 12:08
 */
@RequestMapping("/seller/product")
@Slf4j
@Controller
public class SellerProductController {

    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    CategoryService categoryService;

    /**
     * 商品列表页面
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> m = new HashMap<>();
        Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page - 1,size));
        m.put("productInfoPage",productInfoPage);
        m.put("currentPage",page);
        m.put("size",size);
        return new ModelAndView("/product/list",m);
    }

    /**
     * 商品上架
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(String productId){
        Map<String ,Object> m = new HashMap<>();
        m.put("url","/sell/seller/product/list");
        try {
            productInfoService.onSale(productId);
        }catch (SellException e){
            log.error("[商品上架]上架失败");
            m.put("msg",e.getMessage());
            return new ModelAndView("/common/error",m);
        }
        return new ModelAndView("/common/success",m);
    }

    /**
     * 商品下架
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(String productId){
        Map<String ,Object> m = new HashMap<>();
        m.put("url","/sell/seller/product/list");
        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            log.error("[商品下架]下架失败");
            m.put("msg",e.getMessage());
            return new ModelAndView("/common/error",m);
        }
        return new ModelAndView("/common/success",m);
    }

    /**
     * 新增/修改商品页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(required = false) String productId){
        Map<String,Object> m = new HashMap<>();
        if (!StringUtils.isEmpty(productId)){
            //如果Id不为空则为修改商品操作
            ProductInfo productInfo = productInfoService.findOne(productId);
            m.put("productInfo",productInfo);
        }
        List<ProductCategory> categories = categoryService.findAll();
        m.put("categories",categories);
        return new ModelAndView("/product/index",m);
    }

    /**
     * 保存/修改操作
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult){
        Map<String, Object> m = new HashMap<>();
        m.put("url","/sell/seller/product/list");
        if (bindingResult.hasErrors()){
            //如果参数校验失败
            log.error("【保存/修改商品】操作参数不正确，productForm={}",productForm);
            m.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error",m);
        }

        try {
            ProductInfo productInfo = new ProductInfo();
            if (StringUtils.isEmpty(productForm.getProductId())){
                //如果id为空/不存在，则说明是新增操作
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            else {
                //否则为修改操作
                productInfo = productInfoService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);
        }catch (SellException e){
            log.error("【保存/修改商品】访问数据库失败");
            m.put("msg",e.getMessage());
            return new ModelAndView("common/error",m);
        }
        return new ModelAndView("common/success",m);
    }

}
