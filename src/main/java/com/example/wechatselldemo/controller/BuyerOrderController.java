package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.domain.form.OrderForm;
import com.example.wechatselldemo.domain.vo.ResultVo;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.utils.OrderForm2OrderDTOConverter;
import com.example.wechatselldemo.utils.ResultVoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/sell/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public ResultVo create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确， orderForm-{}", orderForm);
            throw new SellException(ResultEnum.ORDER_FORM_ERROR);
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空， ");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO create = orderMasterService.create(orderDTO);
        Map<String, String> data = new HashMap<>();
        data.put("orderId", create.getOrderId());

        return ResultVoUtils.success(data);
    }

    /**
     * 取消订单
     */


    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ResultVo list(@RequestParam("openid") String openid,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (Objects.isNull(openid)) {
            log.error("【查询订单】订单列表为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Page<OrderDTO> list = orderMasterService.findList(openid, PageRequest.of(page, size));
        return ResultVoUtils.success(list.getContent());
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ResultVo orderDetails() {
        return null;
    }
}
