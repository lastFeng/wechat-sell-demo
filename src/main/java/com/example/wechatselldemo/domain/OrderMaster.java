package com.example.wechatselldemo.domain;

import com.example.wechatselldemo.enums.OrderStatusEnum;
import com.example.wechatselldemo.enums.PayStatusEnum;
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
@Table(name = "order_master")
@Entity
@DynamicUpdate
@Data
public class OrderMaster {
    /** 主键 */
    @Id
    private String orderId;

    /** 买家名 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openId */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 */
    @Column(columnDefinition = "tinyint(3)")
    private Integer orderStatus = OrderStatusEnum.NEW.getStatus();

    /** 支付状态 */
    @Column(columnDefinition = "tinyint(3)")
    private Integer payStatus = PayStatusEnum.WAIT.getStatus();

    /** 创建时间 */
    private transient Date createTime;

    /** 更新时间 */
    private transient Date updateTime;

    public OrderMaster() {
    }

    public OrderMaster(String orderId, String buyerName, String buyerPhone, String buyerAddress,
                       String buyerOpenid, BigDecimal orderAmount, Integer orderStatus, Integer payStatus) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
    }

    public OrderMaster(String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid,
                       BigDecimal orderAmount, Integer orderStatus, Integer payStatus) {
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
    }
}
