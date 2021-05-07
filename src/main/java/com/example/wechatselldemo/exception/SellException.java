package com.example.wechatselldemo.exception;

import com.example.wechatselldemo.enums.ResultEnum;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
