package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 根据商品状态查找商品列表
     * @param status 状态 0-正常， 1-下架
     * @return list
     */
    List<ProductInfo> findByProductStatus(Integer status);

    /**
     * 分页查找
     * @param status
     * @param pageable
     * @return
     */
    Page<ProductInfo> findByProductStatus(Integer status, Pageable pageable);
}
