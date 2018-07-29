package com.wxsell.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * http请求返回的最外层对象
 * Created By Cx On 2018/6/10 22:53
 */
@Data
@AllArgsConstructor
public class ResultVO<T> {
    //错误码，0正确
    private Integer code;
    //错误信息
    private String msg;
    //数据
    private T data;

    public ResultVO() {
    }
}
