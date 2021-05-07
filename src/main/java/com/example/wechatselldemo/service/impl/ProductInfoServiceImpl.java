package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.dao.ProductInfoRepository;
import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.dto.CartDTO;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findById(String productId) {
        return productInfoRepository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findAll();
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return productInfoRepository.findByProductStatus(productStatus);
    }

    @Override
    public Page<ProductInfo> findByProductStatus(Integer productStatus, Pageable pageable) {
        return productInfoRepository.findByProductStatus(productStatus, pageable);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> carts) {
        for (CartDTO cart : carts) {
            ProductInfo product = productInfoRepository.findById(cart.getProductId()).orElse(null);
            if (Objects.isNull(product)) {
                log.error("商品:{} - 不存在", cart.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            product.setProductStock(Integer.MAX_VALUE - cart.getProductQuantity() <= product.getProductStock() ?
                    Integer.MAX_VALUE : cart.getProductQuantity() + product.getProductStock());

            productInfoRepository.save(product);
            log.info("增加库存成功：商品id-{} 增加数量-{} 库存剩余量-{}", product.getProductId(),
                    cart.getProductQuantity(), product.getProductStock());
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> carts) {
        for (CartDTO cart : carts) {
            ProductInfo product = productInfoRepository.findById(cart.getProductId()).orElse(null);
            if (Objects.isNull(product)) {
                log.error("商品:{} - 不存在", cart.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            if (product.getProductStock() - cart.getProductQuantity() < 0) {
                log.error("商品库存-{}不足，无法扣除相应数量-{}", product.getProductStock(), cart.getProductQuantity());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            product.setProductStock(product.getProductStock() - cart.getProductQuantity());
            productInfoRepository.save(product);
            log.info("扣减库存成功：商品id-{}，扣减数量-{}， 剩余数量-{}", product.getProductId(),
                    cart.getProductQuantity(), product.getProductStock());
        }
    }
}
