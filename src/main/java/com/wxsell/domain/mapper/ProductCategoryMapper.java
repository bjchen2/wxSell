package com.wxsell.domain.mapper;

import com.wxsell.domain.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 使用mybatis注解查询（与项目无关，仅学习用）
 * 使用前需要在Application项目配置类中添加@MapperScan注解标注需要扫描的mapper包
 * 注意：虽然可以在service层直接引用mapper，但最好还是分层，在dao层引用mapper，在service层引用dao
 * Created By Cx On 2018/7/31 12:07
 */
public interface ProductCategoryMapper {
    //#{a}:表示从map中获取一个key为a的value注入，
    //列名不能使用驼峰命名，如：category_name不能写成categoryName
    @Insert("insert into product_category(category_name,category_type) values (#{a}, #{b})")
    int insertByMap(Map<String,Object> m);

    //列名和对象属性均不能使用驼峰命名
    @Insert("insert into product_category(category_name,category_type) values (#{categoryName}, #{categoryType})")
    int insertByObject(ProductCategory productCategory);

    //要使用@Results注解把数据库中的字段和ProductCategory对象字段对应起来，否则返回null
    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_type" , property = "categoryType"),
            @Result(column = "category_name" , property = "categoryName"),
            @Result(column = "category_id" , property = "categoryId"),
    })
    ProductCategory findByCategoryType(int categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_type" , property = "categoryType"),
            @Result(column = "category_name" , property = "categoryName"),
            @Result(column = "category_id" , property = "categoryId"),
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    //当传多个参数时，要用@Param注解指明参数名
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateCategoryNameByType(@Param("categoryType")int categoryType,@Param("categoryName")String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int delByCategoryType(int categoryType);

    ProductCategory selectByCategoryType(int categoryType);
}
