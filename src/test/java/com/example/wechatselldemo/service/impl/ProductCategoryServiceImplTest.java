package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.domain.ProductCategory;
import com.example.wechatselldemo.service.ProductCategoryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/6
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Order(2)
    @Test
    void findOne() {
        List<ProductCategory> all = productCategoryService.findAll();
        assertNotNull(all);
        ProductCategory one = productCategoryService.findOne(all.get(0).getCategoryId());
        assertNotNull(one);
    }

    @Order(2)
    @Test
    void findAll() {
        List<ProductCategory> all = productCategoryService.findAll();
        assertNotNull(all);
    }

    @Order(2)
    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> all = productCategoryService.findAll();
        assertNotNull(all);
        List<Integer> list = new ArrayList<>();
        list.add(all.get(0).getCategoryType());
        List<ProductCategory> typeIn = productCategoryService.findByCategoryTypeIn(list);
        assertNotNull(typeIn);
    }

    @Order(2)
    @Test
    void findByCategoryType() {
        List<ProductCategory> all = productCategoryService.findAll();
        assertNotNull(all);
        List<ProductCategory> byCategoryType = productCategoryService.findByCategoryType(all.get(0).getCategoryType());
        assertNotNull(byCategoryType);
    }

    @Order(1)
    @Test
    void save() {
        ProductCategory category = new ProductCategory("test", 1);
        ProductCategory save = productCategoryService.save(category);
        assertNotNull(save);
    }
}