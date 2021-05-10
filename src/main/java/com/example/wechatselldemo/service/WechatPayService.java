package com.example.wechatselldemo.service;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;

/**
 * @author : guoweifeng
 * @date : 2021/5/8
 */
public interface WechatPayService {

    /***
     * 创建订单
     * @param orderDTO
     */
    WxPayMpOrderResult create(OrderDTO orderDTO);

    /***
     * 订单支付返回回调
     */
    String pay(WxPayOrderNotifyResult notifyResult);

    /**订单退款*/
    WxPayRefundResult refund(WxPayRefundRequest request);
}
