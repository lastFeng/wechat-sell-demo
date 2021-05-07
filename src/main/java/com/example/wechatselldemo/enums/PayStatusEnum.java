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
    SUCCESS(1, "已支付"); // 支付完成

    private Integer status;
    private String message;

    PayStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
