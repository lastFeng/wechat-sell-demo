package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    /***
     * 通过订单id查询订单详情
     * @param orderId 订单id
     * @return list
     */
    List<OrderDetail> findByOrderId(String orderId);
}
