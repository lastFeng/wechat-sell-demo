package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.domain.OrderDetail;
import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterService orderMasterService;

    @Order(1)
    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("test");
        orderDTO.setBuyerPhone("13111111111");
        orderDTO.setBuyerAddress("test");
        orderDTO.setBuyerOpenid("123456");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(989);
        orderDetailList.add(orderDetail);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO dto = orderMasterService.create(orderDTO);
        assertNotNull(dto.getOrderId());
    }

    @Order(2)
    @Test
    void findOne() {
        OrderDTO one = orderMasterService.findOne("123456","1620372989529686257");
        assertNotNull(one);
    }

    @Order(2)
    @Test
    public void findList() {
        Page<OrderDTO> list = orderMasterService.findList("123456", PageRequest.of(0, 10));
        assertNotNull(list);
        System.out.println(list.getContent());
    }

    @Order(2)
    @Test
    public void cancel() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1620372327817568935");
        OrderDTO cancel = orderMasterService.cancel(orderDTO);
        assertNotNull(cancel);
    }
}