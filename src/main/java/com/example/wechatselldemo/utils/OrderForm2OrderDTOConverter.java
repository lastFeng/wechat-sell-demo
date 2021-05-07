package com.example.wechatselldemo.utils;

import com.example.wechatselldemo.domain.OrderDetail;
import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.domain.form.OrderForm;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class OrderForm2OrderDTOConverter {
    public static final JsonMapper objectMapper = JsonMapper.INSTANCE;
    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        orderDTO.setOrderDetailList(objectMapper.fromJson(orderForm.getItems(),
                objectMapper.buildCollectionType(List.class, OrderDetail.class)));
        return orderDTO;
    }
}
