package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 订单状态枚举类
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),  // 新下订单
    FINISHED(1, "完成"), // 完成订单
    CANCEL(2, "已取消"); // 取消订单

    private Integer status;
    private String message;

    OrderStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
