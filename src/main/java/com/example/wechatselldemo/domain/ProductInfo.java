package com.example.wechatselldemo.domain;

import com.example.wechatselldemo.enums.ProductStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * DynamicUpdate: 自动更新
 */
@Table(name = "product_info")
@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    /**商品信息主键*/
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 图片 */
    private String productIcon;

    /*** 商品状态 */
    @Column(columnDefinition = "tinyint(3)")
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号 */
    private Integer productType;

    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock,
                       String productDescription, String productIcon, Integer productStatus, Integer productType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.productType = productType;
    }

    public ProductInfo(String productName, BigDecimal productPrice, Integer productStock,
                       String productDescription, String productIcon, Integer productStatus, Integer productType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.productType = productType;
    }
}
