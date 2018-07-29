package com.wxsell.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wxsell.enums.ProductStatusEnum;
import com.wxsell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 * Created By Cx On 2018/6/10 14:56
 */
//@DynamicUpdate注解，使update字段自动更新,如updateTime
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class ProductInfo {
    @Id
    //商品id,使用string，使用KeyUtil手动生成Id，防止多线程冲突，模拟大型项目
    private String productId;
    //商品名称
    private String productName;
    //商品单价
    private BigDecimal productPrice;
    //商品库存
    private Integer productStock;
    //商品描述
    private String productDescription;
    //商品图片url
    private String productIcon;
    //商品状态,0正常1下架
    private Integer productStatus;
    //商品类目编号
    private Integer categoryType = ProductStatusEnum.UP.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
