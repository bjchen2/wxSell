package com.wxsell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * Created By Cx On 2018/6/10 23:02
 */
@Data
public class ProductInfoVO {
    //商品id,使用string防止爆long，模拟大型项目
    @JsonProperty("id")
    private String productId;
    //商品名称
    @JsonProperty("name")
    private String productName;
    //商品单价
    @JsonProperty("price")
    private BigDecimal productPrice;
    //商品描述
    @JsonProperty("description")
    private String productDescription;
    //商品图片url
    @JsonProperty("icon")
    private String productIcon;
}
