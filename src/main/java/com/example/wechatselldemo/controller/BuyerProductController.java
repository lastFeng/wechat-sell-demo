package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.ProductCategory;
import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.vo.ProductInfoVo;
import com.example.wechatselldemo.domain.vo.ProductVo;
import com.example.wechatselldemo.domain.vo.ResultVo;
import com.example.wechatselldemo.service.ProductCategoryService;
import com.example.wechatselldemo.service.ProductInfoService;
import com.example.wechatselldemo.utils.ResultVoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@RestController
@RequestMapping("/sell/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ResultVo list() {
        List<ProductInfo> productInfos = productInfoService.findAll();
        List<Integer> types = productInfos.stream().map(ProductInfo::getProductType)
                .collect(collectingAndThen(toSet(), ArrayList::new));
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(types);
        List<ProductVo> list = new ArrayList<>();

        for (ProductCategory category : categories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategory(category.getCategoryType());
            productVo.setCategoryName(category.getCategoryName());
            List<ProductInfoVo> productInfoVos = new ArrayList<>();

            for (ProductInfo productInfo : productInfos) {
                if (Objects.equals(productInfo.getProductType(), category.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productVo.setProductInfoVos(productInfoVos);
            list.add(productVo);
        }

        return ResultVoUtils.success(list);
    }
}
