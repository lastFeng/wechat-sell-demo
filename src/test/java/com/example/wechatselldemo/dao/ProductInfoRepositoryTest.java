package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderDetail;
import com.example.wechatselldemo.domain.ProductInfo;
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
class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository orderDetailRepository;

    @Order(2)
    @Test
    public void testSelect() {
        List<ProductInfo> all = orderDetailRepository.findAll();
        assertNotNull(all);
        assertEquals(1, all.size(), "Size not one");
    }

    @Order(1)
    @Test
    public void testSave() {
        ProductInfo orderDetail = new ProductInfo("1", "test",
                BigDecimal.valueOf(12.23), 999, "", "", 1, 1);
        ProductInfo save = orderDetailRepository.save(orderDetail);
        assertNotNull(save);
    }

    @Order(2)
    @Test
    public void testFindStatus() {
        List<ProductInfo> byProductStatus = orderDetailRepository.findByProductStatus(1);
        assertEquals(1, byProductStatus.size(), "size not equals one");
    }

    @Order(2)
    @Test
    public void testFindStatusPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductInfo> byProductStatus = orderDetailRepository.findByProductStatus(1, pageable);
        assertEquals(1, byProductStatus.getTotalElements(), "size not equals one");
    }

    @Order(2)
    @Test
    public void testUpdate() {
        List<ProductInfo> all = orderDetailRepository.findAll();
        assertNotNull(all);
        ProductInfo orderDetail = all.get(0);
        orderDetail.setProductPrice(BigDecimal.valueOf(9.99));
        ProductInfo save = orderDetailRepository.save(orderDetail);
        assertNotNull(save);
    }

    @Order(3)
    @Test
    public void testDelete() {
        orderDetailRepository.deleteAll();
    }

}