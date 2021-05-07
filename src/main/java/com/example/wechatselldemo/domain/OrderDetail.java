package com.example.wechatselldemo.domain;

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
@Table(name = "order_detail")
@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    /*** 主键 */
    @Id
    private String detailId;

    /*** 订单id */
    private String orderId;

    /*** 商品id */
    private String productId;

    /*** 商品名称 */
    private String productName;

    /*** 商品价格 */
    private BigDecimal productPrice;

    /*** 商品数量 */
    private Integer productQuantity;

    /*** 商品图片 */
    private String productIcon;

    /*** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /*** 更新时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public OrderDetail() {
    }

    public OrderDetail(String detailId, String orderId, String productId,
                       String productName, BigDecimal productPrice, Integer productQuantity, String productIcon) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
    }

    public OrderDetail(String orderId, String productId, String productName,
                       BigDecimal productPrice, Integer productQuantity, String productIcon) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
    }
}
