package com.wxsell.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 类目表
 * Created By Cx On 2018/6/10 0:22
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class ProductCategory {

    //类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {

        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
