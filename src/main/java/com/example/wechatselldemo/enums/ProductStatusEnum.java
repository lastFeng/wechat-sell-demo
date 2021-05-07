package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 商品状态枚举类
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在售"),  // 正常
    DOWN(1, "下架"); // 下架
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
