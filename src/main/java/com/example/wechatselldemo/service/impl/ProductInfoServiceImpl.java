package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.dao.ProductInfoRepository;
import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.dto.CartDTO;
import com.example.wechatselldemo.enums.ProductStatusEnum;
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
                log.error("??????:{} - ?????????", cart.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            product.setProductStock(Integer.MAX_VALUE - cart.getProductQuantity() <= product.getProductStock() ?
                    Integer.MAX_VALUE : cart.getProductQuantity() + product.getProductStock());

            productInfoRepository.save(product);
            log.info("???????????????????????????id-{} ????????????-{} ???????????????-{}", product.getProductId(),
                    cart.getProductQuantity(), product.getProductStock());
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> carts) {
        for (CartDTO cart : carts) {
            ProductInfo product = productInfoRepository.findById(cart.getProductId()).orElse(null);
            if (Objects.isNull(product)) {
                log.error("??????:{} - ?????????", cart.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            if (product.getProductStock() - cart.getProductQuantity() < 0) {
                log.error("????????????-{}?????????????????????????????????-{}", product.getProductStock(), cart.getProductQuantity());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            product.setProductStock(product.getProductStock() - cart.getProductQuantity());
            productInfoRepository.save(product);
            log.info("???????????????????????????id-{}???????????????-{}??? ????????????-{}", product.getProductId(),
                    cart.getProductQuantity(), product.getProductStock());
        }
    }

    @Override
    @Transactional
    public void downProduct(String productId) {
        ProductInfo product = productInfoRepository.findById(productId).orElse(null);
        if (Objects.isNull(product)) {
            log.error("?????????????????????????????????????????????????????????productId-{}", productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (Objects.equals(product.getProductStatus(), ProductStatusEnum.DOWN.getCode())) {
            log.error("????????????????????????????????????????????????????????????");
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        product.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoRepository.save(product);
    }
}
