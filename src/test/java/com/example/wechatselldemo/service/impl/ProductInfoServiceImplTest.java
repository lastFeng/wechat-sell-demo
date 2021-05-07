package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.service.ProductInfoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Order(2)
    @Test
    void findAll() {
        List<ProductInfo> all = productInfoService.findAll();
        assertNotNull(all);
        assertEquals(1, all.size(), "Size not one");
    }

    @Order(1)
    @Test
    void save() {
        ProductInfo orderDetail = new ProductInfo("1", "test",
                BigDecimal.valueOf(12.23), 999, "", "", 1, 1);
        ProductInfo save = productInfoService.save(orderDetail);
        assertNotNull(save);
    }

    @Order(2)
    @Test
    void findByProductStatus() {
        List<ProductInfo> byProductStatus = productInfoService.findByProductStatus(1);
        assertEquals(1, byProductStatus.size(), "size not equals one");
        System.out.println(byProductStatus);
    }

    @Test
    void testFindByProductStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductInfo> byProductStatus = productInfoService.findByProductStatus(1, pageable);
        assertEquals(1, byProductStatus.getTotalElements(), "size not equals one");
    }
}