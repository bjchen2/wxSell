package com.wxsell.controller;

import com.wxsell.VO.ProductInfoVO;
import com.wxsell.VO.ProductVO;
import com.wxsell.VO.ResultVO;
import com.wxsell.domain.ProductCategory;
import com.wxsell.domain.ProductInfo;
import com.wxsell.service.CategoryService;
import com.wxsell.service.ProductInfoService;
import com.wxsell.utils.ResultUtil;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家端产品有关接口
 * Created By Cx On 2018/6/10 22:50
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductInfoService productInfoService;

    @GetMapping("/list")
    public ResultVO list() {
        //获取所有上架商品
        List<ProductInfo> products = productInfoService.findUpAll();
//        List<Integer> productTypes = new ArrayList<>();
//        for (ProductInfo p : products){
//            productTypes.add(p.getCategoryType());
//        }
        //lambda表达式，等价于上述操作
        List<Integer> productTypes = products.stream().map(o -> o.getCategoryType()).collect(Collectors.toList());
        //切记不要for循环一个个找category，这样效率很慢，因为每次查找都要涉及数据库的重新访问
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(productTypes);
        List<ProductVO> productVOs = new ArrayList<>();
        for (ProductCategory pc : categories) {
            //遍历所有已上架的类目
            ProductVO productVO = new ProductVO(pc.getCategoryName(), pc.getCategoryType());
            List<ProductInfoVO> foods = new ArrayList<>();
            for (ProductInfo pi : products) {
                //遍历所有商品，若和当前类目相同，则添加进foods
                if (pi.getCategoryType() == pc.getCategoryType()) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(pi, productInfoVO);
                    foods.add(productInfoVO);
                }
            }
            productVO.setProducts(foods);
            productVOs.add(productVO);
        }
        return ResultUtil.success(productVOs);
    }
}
