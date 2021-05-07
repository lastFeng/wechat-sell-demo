package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 订单状态枚举类
 */
@Getter
public enum OrderStatusEnum {
    /**新下订单*/
    NEW(0, "新订单"),
    /**完成订单*/
    FINISHED(1, "完成"),
    /**取消订单*/
    CANCEL(2, "已取消");

    private final Integer code;
    private final String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
