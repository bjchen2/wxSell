package com.wxsell.exception;

import com.wxsell.enums.ResultEnum;
import lombok.Data;

/**
 * 购买异常类
 * Created By Cx On 2018/6/11 12:20
 */
@Data
public class SellException extends RuntimeException {
    //错误码
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
