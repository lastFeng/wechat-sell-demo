package com.example.wechatselldemo.service.impl;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.service.WechatPayService;
import com.github.binarywang.wxpay.service.PayScoreService;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : guoweifeng
 * @date : 2021/5/8
 * 微信支付wiki：https://github.com/Wechat-Group/WxJava/wiki/%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98%E5%BC%80%E5%8F%91%E6%96%87%E6%A1%A3
 * https://blog.csdn.net/xsg6509/article/details/80342744
 */
@Service
@Slf4j
public class WechatPayServiceImpl implements WechatPayService {

    @Autowired
    private WxPayService payService;

    @Override
    public void create(OrderDTO orderDTO) {

    }
}
