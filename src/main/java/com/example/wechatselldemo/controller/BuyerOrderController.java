package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.domain.form.OrderForm;
import com.example.wechatselldemo.domain.vo.ResultVo;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.utils.OrderForm2OrderDTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sell/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     */
    public ResultVo create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确， orderForm-{}", orderForm);
            throw new SellException(ResultEnum.ORDER_FORM_ERROR);
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        OrderDTO cancel = orderMasterService.cancel(orderDTO);

        return null;
    }

    /**
     * 取消订单
     */

    /**
     * 订单列表
     */

    /**
     * 订单详情
     */
}
