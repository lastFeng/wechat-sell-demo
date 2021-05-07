package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 订单状态枚举类
 */
@Getter
public enum PayStatusEnum {
    WAIT(0, "未支付"),  // 等待支付
    SUCCESS(1, "已支付"), // 支付完成
    REFUND(2, "已退款"); // 已退款

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
