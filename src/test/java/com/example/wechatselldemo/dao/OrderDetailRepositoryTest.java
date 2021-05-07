package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderDetail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Order(2)
    @Test
    public void testSelect() {
        List<OrderDetail> all = orderDetailRepository.findAll();
        assertNotNull(all);
        assertEquals(1, all.size(), "Size not one");
    }

    @Order(1)
    @Test
    public void testSave() {
        OrderDetail orderDetail = new OrderDetail("1", "1", "1", "test",
                BigDecimal.valueOf(12.23), 1, "");
        OrderDetail save = orderDetailRepository.save(orderDetail);
        assertNotNull(save);
    }

    @Order(2)
    @Test
    public void testUpdate() {
        List<OrderDetail> all = orderDetailRepository.findAll();
        assertNotNull(all);
        OrderDetail orderDetail = all.get(0);
        orderDetail.setProductPrice(BigDecimal.valueOf(9.99));
        OrderDetail save = orderDetailRepository.save(orderDetail);
        assertNotNull(save);
    }

    @Order(3)
    @Test
    public void testDelete() {
        orderDetailRepository.deleteAll();
    }
}