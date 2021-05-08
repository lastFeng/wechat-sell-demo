package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.service.WechatPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                       @RequestParam("openId") String openId,
                       @RequestParam("returnUrl") String returnUrl) {
        // 查询订单
        OrderDTO order = orderService.findOne(openId, orderId);
        if (Objects.isNull(order)) {
            log.error("【创建订单】订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 创建

        return returnUrl;
    }
}
