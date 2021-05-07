package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
