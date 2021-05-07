package com.example.wechatselldemo.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * DynamicUpdate: 自动更新
 */
@Table(name = "product_category")
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /** 类目Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /** 类目名 */
    private String categoryName;
    /** 类目类型 */
    private Integer categoryType;
    /** 创建时间 */
    private transient Date createTime;
    /** 更新时间 */
    private transient Date updateTime;

    protected ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
