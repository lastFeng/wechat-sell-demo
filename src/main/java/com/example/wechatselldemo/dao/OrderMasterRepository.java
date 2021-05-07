package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    /***
     * 根据openid进行分页查询订单
     * @param buyerOpenid openId
     * @param pageable pageable
     * @return list
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
