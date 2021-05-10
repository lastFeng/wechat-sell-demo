package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.dao.OrderDetailRepository;
import com.example.wechatselldemo.dao.OrderMasterRepository;
import com.example.wechatselldemo.domain.OrderDetail;
import com.example.wechatselldemo.domain.OrderMaster;
import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.dto.CartDTO;
import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.enums.OrderStatusEnum;
import com.example.wechatselldemo.enums.PayStatusEnum;
import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import com.example.wechatselldemo.service.OrderMasterService;
import com.example.wechatselldemo.service.ProductInfoService;
import com.example.wechatselldemo.utils.KeyUtil;
import com.example.wechatselldemo.utils.OrderMaster2OrderDTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = BigDecimal.ZERO;
        String orderId = KeyUtil.genUniqueKey();
        // 订单详情入库
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo product = productInfoService.findById(orderDetail.getProductId());
            if (Objects.isNull(product)) {
                log.error("查询商品：{} 不存在", orderDetail.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算每个商品商品的总金额
            orderAmount = orderAmount.add(product.getProductPrice().multiply(BigDecimal.valueOf(orderDetail.getProductQuantity())));

            BeanUtils.copyProperties(product, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        log.info("订单入库：{}", orderMaster);

        // 扣库存 - TODO: 会出现超卖的情况！！
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        orderDTO.setOrderId(orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String openId, String orderId) {
        OrderMaster order = orderMasterRepository.findById(orderId).orElse(null);
        if (Objects.isNull(order)) {
            log.error("【订单查找】查询订单不存在， orderId-{}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(openId.toLowerCase(), order.getBuyerOpenid().toLowerCase())) {
            log.error("【订单查找】查询的订单openId不正确，orderId-{}, openId-{}", orderId, openId);
            throw new SellException(ResultEnum.OPENID_ERROR);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orders = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.converterList(orders.getContent());
        return new PageImpl(orderDTOS, orders.getPageable(), orders.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 查看订单状态 TODO: 多线程会存在问题
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("【取消订单】订单不存在，orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(OrderStatusEnum.NEW.getCode(), order.getOrderStatus())) {
            log.error("【取消订单】订单状态不正确， orderId={}，orderStatus={}", order.getOrderId(), order.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_CANCEL_STATUS_ERROR);
        }
        // 更新状态
        order.setOrderStatus(OrderStatusEnum.CANCEL.getCode());

        // 退款
        if (Objects.equals(order.getPayStatus(), PayStatusEnum.SUCCESS.getCode())) {
            // TODO: 退款操作
            order.setPayStatus(PayStatusEnum.REFUND.getCode());
        }

        OrderMaster orderUpdate = orderMasterRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderUpdate.getOrderId());
        if (CollectionUtils.isEmpty(orderDetails)) {
            log.error("【取消订单】订单中无商品详情，orderId={}", orderUpdate.getOrderId());
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        List<CartDTO> carts = orderDetails.stream().map(item -> new CartDTO(item.getProductId(), item.getProductQuantity()))
                .collect(Collectors.toList());
        // 返回库存
        productInfoService.increaseStock(carts);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("【完成订单】订单不存在，orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(OrderStatusEnum.NEW.getCode(), order.getOrderStatus())) {
            log.error("【完成订单】订单状态不正确， orderId={}，orderStatus={}", order.getOrderId(), order.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_CANCEL_STATUS_ERROR);
        }
        // 更新状态
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        orderMasterRepository.save(order);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("【支付订单】订单不存在，orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(order.getPayStatus(), PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】订单支付状态不正确，orderId={}，payStatus={}", order.getOrderId(), order.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAID_STATUS_ERROR);
        }

        if (Math.abs(orderDTO.getOrderAmount().doubleValue() - order.getOrderAmount().multiply(BigDecimal.valueOf(100)).doubleValue()) > 0) {
            log.error("【支付订单】订单支付金额校验不正确，orderId-{}, payTotalFee-{}, orderTotalFee-{}",
                    orderDTO.getOrderId(), orderDTO.getOrderAmount().doubleValue(),
                    order.getOrderAmount().multiply(BigDecimal.valueOf(100)).doubleValue());
            throw new SellException(ResultEnum.WECHAT_MP_ORDER_PAY_FEE_ERROR);
        }
        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterRepository.save(order);
        return orderDTO;
    }
}
