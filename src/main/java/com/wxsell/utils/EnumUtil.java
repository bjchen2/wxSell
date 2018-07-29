package com.wxsell.utils;

import com.wxsell.enums.CodeEnum;

/**
 * 枚举工具类
 * Created By Cx On 2018/7/25 14:27
 */
public class EnumUtil {
    //遍历枚举类，查询符合code值的枚举,并返回该枚举
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        //getEnumConstants()作用：
        //以声明顺序返回一个数组，该数组包含构成此 Class 对象所表示的枚举类的值，或者在此 Class 对象不表示枚举类型时返回 null
        for (T each : enumClass.getEnumConstants()){
            if (each.getCode().equals(code)) return each;
        }
        return null;
    }
}
