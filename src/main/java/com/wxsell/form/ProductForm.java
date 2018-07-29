package com.wxsell.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品表单参数验证类
 * Created By Cx On 2018/7/26 23:44
 */
@Data
public class ProductForm {
    //商品id,使用string，使用KeyUtil手动生成Id，防止多线程冲突，模拟大型项目
    private String productId;
    //商品名称
    @NotBlank(message = "姓名不能为空")
    private String productName;
    //商品单价
    @NotNull(message = "价格不能为空")
    private BigDecimal productPrice;
    //商品库存
    @NotNull(message = "库存不能为空")
    @Min(value = 0,message = "库存不能小于0")
    private Integer productStock;
    //商品描述
    private String productDescription;
    //商品图片url
    private String productIcon;
    //商品类目编号
    @NotNull(message = "商品类目不能为空")
    private Integer categoryType;
}
