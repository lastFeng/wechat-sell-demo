package com.example.wechatselldemo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {
    /**商品id*/
    private String productId;
    /***商品数量*/
    private Integer productQuantity;
}
