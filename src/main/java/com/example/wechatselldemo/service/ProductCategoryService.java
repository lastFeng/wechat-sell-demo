package com.example.wechatselldemo.service;

import com.example.wechatselldemo.domain.ProductCategory;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/6
 */
public interface ProductCategoryService {
    /**
     * 根据主键id查找一条数据
     * @param categoryId id
     * @return ProductCategory
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 根据查找所有数据
     * @return list
     */
    List<ProductCategory> findAll();

    /***
     * 通过类型查找
     * @param categoryTypeList typeList
     * @return list
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 通过类型查找
     * @param categoryType type
     * @return list
     */
    List<ProductCategory> findByCategoryType(Integer categoryType);

    /***
     * 保存
     * @param productCategory ProductCategory
     * @return ProductCategory
     */
    ProductCategory save(ProductCategory productCategory);
}
