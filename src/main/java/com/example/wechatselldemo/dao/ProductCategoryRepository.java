package com.example.wechatselldemo.dao;

import com.example.wechatselldemo.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/6
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /***
     * 根据类目列表类型查找类目
     * @param categoryTypeList 类目列表
     * @return list
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 根据类目类型查找类目列表
     * @param categoryType 列目类型
     * @return list
     */
    List<ProductCategory> findByCategoryType(Integer categoryType);
}
