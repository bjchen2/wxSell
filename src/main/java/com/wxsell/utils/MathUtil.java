package com.wxsell.utils;

/**
 * Created By Cx On 2018/7/23 21:34
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较金额是否相等
     */
    public static Boolean equals(Double a,Double b){
        return Math.abs(a - b) < MONEY_RANGE;
    }
}
