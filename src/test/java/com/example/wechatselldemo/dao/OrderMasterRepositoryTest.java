package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.OrderMaster;
import com.example.wechatselldemo.enums.PayStatusEnum;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Order(1)
    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("123");
        orderMaster.setBuyerAddress("123");
        orderMaster.setBuyerOpenid("123");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setOrderAmount(BigDecimal.valueOf(12.23));

        OrderMaster save = orderMasterRepository.save(orderMaster);
        assertNotNull(save);
    }

    @Order(2)
    @Test
    void findByBuyerOpenid() {
        OrderMaster orderMaster = orderMasterRepository.findByBuyerOpenid("123", PageRequest.of(0, 5)).getContent().get(0);
        assertNotNull(orderMaster);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster save = orderMasterRepository.save(orderMaster);
        assertNotNull(save);
    }

    @Order(3)
    @Test
    public void deleteAll() {
        orderMasterRepository.deleteAll();
    }
}