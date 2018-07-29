package com.wxsell.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Created By Cx On 2018/7/27 14:43
 */
@Data
public class CategoryForm {
    //类目id
    private Integer categoryId;
    //类目名称
    @NotBlank(message = "名称不能为空")
    private String categoryName;
    //类目编号
    @Min(value = 0,message = "type必须大于0")
    private Integer categoryType;
}
