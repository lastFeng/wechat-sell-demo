package com.example.wechatselldemo.utils;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.domain.form.OrderForm;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class OrderForm2OrderDTOConverter {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());


        return orderDTO;
    }
}
