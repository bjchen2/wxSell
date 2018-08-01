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
import java.io.Serializable;
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
public class ProductInfo implements Serializable{

    /**
     * 这里使用GenerateSerialVersionUID插件自动生成序列化ID（快捷键为alt+z,可自己在setting中搜索GenerateSerial更改）
     *
     * 首先，必须知道，反序列化时会从序列化的二进制流中读取serialVersionUID与与本地实体类中的serialVersionUID进行比较
     * 若serialVersionUID相同，才能进行反序列化。
     *
     * Java序列化机制会根据编译时的class自动生成一个serialVersionUID作为序列化版本比较，
     * 但每次编译生成的class产生的serialVersionUID不同
     * 这个时候再反序列化时便会出现serialVersionUID不一致，导致反序列化失败。
     */
    private static final long serialVersionUID = -5165188852744090555L;

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
