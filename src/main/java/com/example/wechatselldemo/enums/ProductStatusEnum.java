package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 商品状态枚举类
 */
@Getter
public enum ProductStatusEnum {
    /**正常*/
    UP(0, "在售"),
    /**下架*/
    DOWN(1, "下架");
    private final Integer code;
    private final String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
