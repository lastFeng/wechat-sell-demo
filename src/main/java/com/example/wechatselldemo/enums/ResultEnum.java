package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_CANCEL_STATUS_ERROR(14, "取消订单状态不正确"),
    ORDER_UPDATE_ERROR(15, "订单更新失败"),
    ORDER_PAID_STATUS_ERROR(16, "订单支付状态不正确"),
    ORDER_FORM_ERROR(17, "订单表参数不正确"),
    CART_EMPTY(18, "购物车不能为空"),
    PARAM_ERROR(19, "参数不正确"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
