package com.example.wechatselldemo.domain.vo.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Data
public class ProductVo {
    /**商品类型名*/
    @JsonProperty("name")
    private String categoryName;
    /**商品类型*/
    @JsonProperty("type")
    private Integer category;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVos;
}
