package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.service.WechatPayService;
import com.example.wechatselldemo.utils.JsonMapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.converter.WxPayOrderNotifyResultConverter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * @author : guoweifeng
 * @date : 2021/5/8
 */
@Controller
@RequestMapping("/sell/pay")
@Slf4j
public class WechatPayController {

    @Autowired
    private OrderMasterService orderService;

    @Autowired
    private WechatPayService wechatPayService;

    private JsonMapper mapper = JsonMapper.INSTANCE;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("openId") String openId,
                               @RequestParam("returnUrl") String returnUrl, Model model) {
        // 查询订单
        OrderDTO order = orderService.findOne(openId, orderId);
        if (Objects.isNull(order)) {
            log.error("【创建订单】订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 创建
        WxPayMpOrderResult wxPayMpOrderResult = wechatPayService.create(order);
        model.addAttribute("orderResponse", wxPayMpOrderResult);
        return new ModelAndView(returnUrl, "orderModel", model);
    }

    /***
     * 订单回调 这里要对接收的String数据进行解密
     * @return
     */
    @PostMapping("/notify")
    @ResponseBody
    public String wechatOrderNotify(String notify) {
        // TODO:这里需要进行解密，这里假装是给的没有加密的内容
        WxPayOrderNotifyResult orderNotifyResult = WxPayOrderNotifyResult.fromXML(notify);

        return wechatPayService.pay(orderNotifyResult);
    }

    @PostMapping("/refund")
    @ResponseBody
    public String refund(String body) {
        OrderDTO order = mapper.fromJson(body, OrderDTO.class);
        orderService.cancel(order);
        return "success";
    }
}
