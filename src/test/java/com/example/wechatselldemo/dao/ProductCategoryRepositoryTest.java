package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.ProductCategory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : guoweifeng
 * @date : 2021/5/6
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * 先执行这个
     */
    @Test
    @Order(1)
    public void testSave() {
        ProductCategory category = new ProductCategory("test", 1);
        ProductCategory save = productCategoryRepository.save(category);
        Assertions.assertNotNull(save);
    }

    @Test
    @Order(2)
    public void testSelectOne() {
        List<ProductCategory> all = productCategoryRepository.findAll();
        Assertions.assertEquals(1, all.size(), "size not equals one");
        Optional<ProductCategory> byId = productCategoryRepository.findById(all.get(0).getCategoryId());
        byId.ifPresent(Assertions::assertNotNull);
    }

    @Test
    @Order(2)
    public void testUpdate() {
        ProductCategory productCategory = productCategoryRepository.findById(1).orElse(null);
        assert productCategory != null;
        productCategory.setCategoryType(2);
        ProductCategory save = productCategoryRepository.save(productCategory);
        Assertions.assertNotNull(save);
    }

    @Test
    @Order(3)
    public void testFind() {
        List<ProductCategory> type = productCategoryRepository.findByCategoryType(2);
        Assertions.assertNotNull(type);
    }

    @Test
    @Order(3)
    public void testFindList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        List<ProductCategory> types = productCategoryRepository.findByCategoryTypeIn(list);
        Assertions.assertNotNull(types);
    }

    /**
     * 最后执行这个
     */
    @Test
    @Order(4)
    public void testDeleteOne() {
        productCategoryRepository.deleteAll();
    }
}