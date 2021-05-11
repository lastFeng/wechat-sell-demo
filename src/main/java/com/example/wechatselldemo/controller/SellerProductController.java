package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.ProductCategory;
import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.domain.vo.product.ProductInfoVo;
import com.example.wechatselldemo.domain.vo.product.ProductVo;
import com.example.wechatselldemo.service.ProductCategoryService;
import com.example.wechatselldemo.service.ProductInfoService;
import com.example.wechatselldemo.utils.JsonMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/sell/seller/product/")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private JsonMapper mapper = JsonMapper.INSTANCE;

    @GetMapping("/list")
    public ResponseEntity<Page<ProductVo>> findProductByPageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ProductInfo> products = productInfoService.findAll(PageRequest.of(page, size));
        List<ProductInfo> content = products.getContent();
        List<Integer> types = content.stream().map(ProductInfo::getProductType)
                .collect(collectingAndThen(toSet(), ArrayList::new));
        List<ProductCategory> categories = productCategoryService.findByCategoryTypeIn(types);
        List<ProductVo> list = new ArrayList<>();

        for (ProductCategory category : categories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategory(category.getCategoryType());
            productVo.setCategoryName(category.getCategoryName());
            List<ProductInfoVo> productInfoVos = new ArrayList<>();

            for (ProductInfo productInfo : content) {
                if (Objects.equals(productInfo.getProductType(), category.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productVo.setProductInfoVos(productInfoVos);
            list.add(productVo);
        }
        Page<ProductVo> result = new PageImpl<ProductVo>(list, products.getPageable(), products.getTotalElements());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfo> findByProductId(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productInfoService.findById(productId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> newProduct(@RequestBody String body) {
        ProductInfo productInfo = mapper.fromJson(body, ProductInfo.class);
        productInfoService.save(productInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/down")
    public ResponseEntity<Void> downProduct(@RequestParam("productId") String productId) {
        productInfoService.downProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductCategory>> getCateGory() {
        return new ResponseEntity<>(productCategoryService.findAll(), HttpStatus.OK);
    }
}
