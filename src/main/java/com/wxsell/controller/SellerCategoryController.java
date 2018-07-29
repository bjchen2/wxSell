package com.wxsell.controller;

import com.wxsell.domain.ProductCategory;
import com.wxsell.enums.ResultEnum;
import com.wxsell.exception.SellException;
import com.wxsell.form.CategoryForm;
import com.wxsell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Cx On 2018/7/27 14:05
 */
@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 类目列表页
     */
    @GetMapping("/list")
    public ModelAndView list(){
        Map<String,Object> m = new HashMap<>();
        m.put("categories",categoryService.findAll());
        return new ModelAndView("category/list",m);
    }

    /**
     * 类目修改/增加页
     */
    @GetMapping("/index")
    public ModelAndView index(Integer categoryId){
        Map<String,Object> m = new HashMap<>();
        if (categoryId != null) {
            //如果是修改操作
            ProductCategory category = categoryService.getOne(categoryId);
            if (category == null) {
                //如果该id不存在
                m.put("msg", "参数不正确，categoryId不存在");
                m.put("url", "/sell/seller/category/list");
                return new ModelAndView("common/error",m);
            }
            m.put("category", category);
        }
        return new ModelAndView("category/index",m);
    }

    /**
     * 类目修改/增加操作
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult){
        Map<String,Object> m = new HashMap<>();
        m.put("url","/sell/seller/category/list");
        if (bindingResult.hasErrors()){
            log.error("[添加/修改类目]参数不正确,{}",bindingResult.getFieldError().getDefaultMessage());
            m.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error",m);
        }
        ProductCategory category;
        if (categoryForm.getCategoryId() != null){
            //如果是修改操作，判断categoryId是否存在
            category = categoryService.getOne(categoryForm.getCategoryId());
            if (category == null){
                log.error("[添加/修改类目]修改失败，类目ID不存在");
                m.put("msg",ResultEnum.CATEGORY_NOT_EXIST.getMsg());
                return new ModelAndView("common/error",m);
            }
        }
        else {
            //如果是新增操作，判断categoryType是否存在
            category = categoryService.findByCategoryType(categoryForm.getCategoryType());
            if (category != null){
                log.error("[添加/修改类目]添加失败，类目type已存在");
                m.put("msg","类目type已存在");
                return new ModelAndView("common/error",m);
            }
            category = new ProductCategory();
        }
        BeanUtils.copyProperties(categoryForm,category);
        categoryService.save(category);
        return new ModelAndView("common/success",m);
    }
}
