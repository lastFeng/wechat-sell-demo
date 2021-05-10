package com.example.wechatselldemo.enums;

import lombok.Getter;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Getter
public enum ResultEnum {
    /**商品不存在*/
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    /**商品库存不正确*/
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    /**订单不存在*/
    ORDER_NOT_EXIST(12, "订单不存在"),
    /**订单详情不存在*/
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    /**取消订单状态不正确*/
    ORDER_CANCEL_STATUS_ERROR(14, "取消订单状态不正确"),
    /**订单更新失败*/
    ORDER_UPDATE_ERROR(15, "订单更新失败"),
    /**订单支付状态不正确*/
    ORDER_PAID_STATUS_ERROR(16, "订单支付状态不正确"),
    /**订单表参数不正确*/
    ORDER_FORM_ERROR(17, "订单表参数不正确"),
    /**购物车不能为空*/
    CART_EMPTY(18, "购物车不能为空"),
    /**参数不正确*/
    PARAM_ERROR(19, "参数不正确"),
    /**OpenId不正确*/
    OPENID_ERROR(20, "用户无权限"),
    /**微信授权失败*/
    WECHAT_MP_ERROR(21, "微信授权失败"),
    /**微信统一下单失败*/
    WECHAT_MP_ORDER_CREATE_ERROR(22, "微信统一下单失败"),
    /**微信支付状态不正确*/
    WECHAT_MP_ORDER_PAY_STATUS_ERROR(23, "微信订单支付状态不正确"),
    /**支付订单金额校验不正确*/
    WECHAT_MP_ORDER_PAY_FEE_ERROR(24, "微信订单支付金额不正确"),
    ;
    /**状态码*/
    private final Integer code;
    /**对应消息*/
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
