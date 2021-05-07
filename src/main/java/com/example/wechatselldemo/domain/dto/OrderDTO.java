package com.example.wechatselldemo.domain.dto;

import com.example.wechatselldemo.domain.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Data
public class OrderDTO {
    /** 主键 */
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
    private Integer orderStatus;

    /** 支付状态 */
    private Integer payStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 订单详情 */
    private List<OrderDetail> orderDetailList;
}
