package com.example.wechatselldemo.utils;

import com.example.wechatselldemo.domain.OrderMaster;
import com.example.wechatselldemo.domain.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converterList(List<OrderMaster> orderMasters) {
        return orderMasters.stream().map(item -> converter(item)).collect(Collectors.toList());
    }
}
