package com.example.wechatselldemo.service;

import com.example.wechatselldemo.domain.dto.OrderDTO;

/**
 * @author : guoweifeng
 * @date : 2021/5/8
 */
public interface WechatPayService {

    /***
     * 创建订单
     * @param orderDTO
     */
    void create(OrderDTO orderDTO);
}
