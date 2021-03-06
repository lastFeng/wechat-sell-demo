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
import com.example.wechatselldemo.service.WechatPayService;
import com.example.wechatselldemo.utils.KeyUtil;
import com.example.wechatselldemo.utils.OrderMaster2OrderDTOConverter;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
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

    @Autowired
    private WechatPayService wechatPayService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = BigDecimal.ZERO;
        String orderId = KeyUtil.genUniqueKey();
        // ??????????????????
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo product = productInfoService.findById(orderDetail.getProductId());
            if (Objects.isNull(product)) {
                log.error("???????????????{} ?????????", orderDetail.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // ????????????????????????????????????
            orderAmount = orderAmount.add(product.getProductPrice().multiply(BigDecimal.valueOf(orderDetail.getProductQuantity())));

            BeanUtils.copyProperties(product, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        // ????????????
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        log.info("???????????????{}", orderMaster);

        // ????????? - TODO: ??????????????????????????????
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
            log.error("?????????????????????????????????????????? orderId-{}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(openId.toLowerCase(), order.getBuyerOpenid().toLowerCase())) {
            log.error("?????????????????????????????????openId????????????orderId-{}, openId-{}", orderId, openId);
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
        return new PageImpl<OrderDTO>(OrderMaster2OrderDTOConverter.converterList(orders.getContent()),
                orders.getPageable(), orders.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // ?????????????????? TODO: ????????????????????????
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("????????????????????????????????????orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(OrderStatusEnum.NEW.getCode(), order.getOrderStatus())) {
            log.error("?????????????????????????????????????????? orderId={}???orderStatus={}", order.getOrderId(), order.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_CANCEL_STATUS_ERROR);
        }
        // ????????????
        order.setOrderStatus(OrderStatusEnum.CANCEL.getCode());

        // ??????
        if (Objects.equals(order.getPayStatus(), PayStatusEnum.SUCCESS.getCode())) {
            WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
                    .outTradeNo(order.getOrderId())
                    .outTradeNo(order.getOrderId())
                    .totalFee(order.getOrderAmount().multiply(BigDecimal.valueOf(100)).intValue())
                    // TODO: ???????????????????????????APP_ID
                    .opUserId("test")
                    .build();
            wechatPayService.refund(request);
            order.setPayStatus(PayStatusEnum.REFUND.getCode());
        }

        OrderMaster orderUpdate = orderMasterRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderUpdate.getOrderId());
        if (CollectionUtils.isEmpty(orderDetails)) {
            log.error("?????????????????????????????????????????????orderId={}", orderUpdate.getOrderId());
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        List<CartDTO> carts = orderDetails.stream().map(item -> new CartDTO(item.getProductId(), item.getProductQuantity()))
                .collect(Collectors.toList());
        // ????????????
        productInfoService.increaseStock(carts);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("????????????????????????????????????orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(OrderStatusEnum.NEW.getCode(), order.getOrderStatus())) {
            log.error("?????????????????????????????????????????? orderId={}???orderStatus={}", order.getOrderId(), order.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_CANCEL_STATUS_ERROR);
        }
        // ????????????
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        orderMasterRepository.save(order);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster order = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (Objects.isNull(order)) {
            log.error("????????????????????????????????????orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if (!Objects.equals(order.getPayStatus(), PayStatusEnum.WAIT.getCode())) {
            log.error("????????????????????????????????????????????????orderId={}???payStatus={}", order.getOrderId(), order.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAID_STATUS_ERROR);
        }

        if (Math.abs(orderDTO.getOrderAmount().doubleValue() - order.getOrderAmount().multiply(BigDecimal.valueOf(100)).doubleValue()) > 0) {
            log.error("??????????????????????????????????????????????????????orderId-{}, payTotalFee-{}, orderTotalFee-{}",
                    orderDTO.getOrderId(), orderDTO.getOrderAmount().doubleValue(),
                    order.getOrderAmount().multiply(BigDecimal.valueOf(100)).doubleValue());
            throw new SellException(ResultEnum.WECHAT_MP_ORDER_PAY_FEE_ERROR);
        }
        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterRepository.save(order);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orders = orderMasterRepository.findAll(pageable);
        return new PageImpl<OrderDTO>(OrderMaster2OrderDTOConverter.converterList(orders.getContent()),
                orders.getPageable(), orders.getTotalElements());
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster order = orderMasterRepository.findById(orderId).orElse(null);
        if (Objects.isNull(order)) {
            log.error("?????????????????????????????????????????? orderId-{}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
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
}
