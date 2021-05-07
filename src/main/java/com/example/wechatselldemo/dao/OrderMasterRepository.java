package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
