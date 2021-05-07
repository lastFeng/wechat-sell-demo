package com.example.wechatselldemo.service;

import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public interface ProductInfoService {
    /**
     * 根据id查找
     * @param productId id
     * @return ProductInfo
     */
    ProductInfo findById(String productId);

    /**
     * 查找所有
     * @return list
     */
    List<ProductInfo> findAll();

    /**
     * 分页查找所有
     * @param pageable
     * @return list
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存
     * @param productInfo productInfo
     * @return ProductInfo
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 根据状态查找所有
     * @param productStatus 状态
     * @return list
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 分页查找
     * @param productStatus status
     * @param pageable pageable
     * @return list
     */
    Page<ProductInfo> findByProductStatus(Integer productStatus, Pageable pageable);

    /***
     * 加库存
     * @param carts
     */
    void increaseStock(List<CartDTO> carts);

    /***
     * 减库存
     * @param carts
     */
    void decreaseStock(List<CartDTO> carts);
}
