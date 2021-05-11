package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.ProductInfo;
import com.example.wechatselldemo.service.ProductInfoService;
import com.example.wechatselldemo.utils.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sell/seller/product/")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    private JsonMapper mapper = JsonMapper.INSTANCE;

    @GetMapping("/list")
    public ResponseEntity<Page<ProductInfo>> findProductByPageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(productInfoService.findAll(PageRequest.of(page, size)), HttpStatus.OK);
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
}
