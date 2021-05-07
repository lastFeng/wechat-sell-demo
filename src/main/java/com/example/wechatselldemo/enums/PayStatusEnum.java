package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 订单状态枚举类
 */
@Getter
public enum PayStatusEnum {
    /**等待支付*/
    WAIT(0, "未支付"),
    /**支付完成*/
    SUCCESS(1, "已支付"),
    /**已退款*/
    REFUND(2, "已退款");

    private final Integer code;
    private final String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
