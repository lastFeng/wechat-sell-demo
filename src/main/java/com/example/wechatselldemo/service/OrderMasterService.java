package com.example.wechatselldemo.service;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface OrderMasterService {
    /***创建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /***查询订单*/
    OrderDTO findOne(String orderId);

    /***查询订单*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /***取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /***完成订单*/
    OrderDTO finish(OrderDTO orderDTO);

    /***支付订单*/
    OrderDTO paid(OrderDTO orderDTO);
}
