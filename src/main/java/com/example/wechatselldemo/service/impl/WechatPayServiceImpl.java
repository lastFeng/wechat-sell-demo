package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.enums.PayStatusEnum;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.service.WechatPayService;
import com.example.wechatselldemo.utils.KeyUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.PayScoreService;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author : guoweifeng
 * @date : 2021/5/8
 * 微信支付wiki：https://github.com/Wechat-Group/WxJava/wiki/%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98%E5%BC%80%E5%8F%91%E6%96%87%E6%A1%A3
 * https://blog.csdn.net/xsg6509/article/details/80342744
 */
@Service
@Slf4j
public class WechatPayServiceImpl implements WechatPayService {

    @Autowired
    private WxPayService payService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public WxPayMpOrderResult create(OrderDTO orderDTO) {
        // 设置订单请求
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setNonceStr(KeyUtil.genUniqueKey());
        request.setBody("test");
        request.setOutTradeNo(orderDTO.getOrderId());
        // 填入的是分，要乘以100
        request.setTotalFee(orderDTO.getOrderAmount().multiply(BigDecimal.valueOf(100)).intValue());
        try {
            request.setSpbillCreateIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        request.setNotifyUrl("http://xxxx.xx/sell/pay/notify");
        request.setTradeType(WxPayConstants.TradeType.JSAPI);
        WxPayMpOrderResult order = null;
        try {
            order = (WxPayMpOrderResult)payService.createOrder(request);
            log.info("【微信预支付订单】- prePareOrder: {}", order);
        } catch (WxPayException e) {
            log.error("【微信统一下单】下单失败：商品订单号：{}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.WECHAT_MP_ORDER_CREATE_ERROR);
        }

        return order;
    }

    @Override
    public String pay(WxPayOrderNotifyResult notifyResult) {

        /**订单号*/
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(notifyResult.getOutTradeNo());
        orderDTO.setOrderId(notifyResult.getOpenid());
        orderDTO.setOrderAmount(BigDecimal.valueOf(notifyResult.getTotalFee()));

        orderMasterService.paid(orderDTO);
        return WxPayNotifyResponse.successResp("OK");
    }

    @Override
    public WxPayRefundResult refund(WxPayRefundRequest request) {
        try {
            return payService.refund(request);
        } catch (WxPayException e) {
            log.error("【微信支付】微信退款失败，orderId-{}， errorMsg-{}", request.getOutTradeNo(), e.getReturnMsg());
            throw new SellException(ResultEnum.WECHAT_MP_ORDER_REFUND_ERROR);
        }
    }
}
