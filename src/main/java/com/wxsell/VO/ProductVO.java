package com.wxsell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 类目概要
 * 有时domain层实体类的某些信息比较 敏感/私密/无用 ，则不返回给前端，所以定义一个VO对象用于响应
 * Created By Cx On 2018/6/10 23:02
 */
@Data
public class ProductVO {
    //则返回的Json数据为name，而不是categoryName
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> products;

    public ProductVO(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductVO() {
    }
}
