package com.mohaymen.store.web.controller;

import com.mohaymen.store.exception.ProductNotFoundException;
import com.mohaymen.store.service.IProductService;
import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final IProductService iProductService;

    ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @GetMapping
    PageResult<ProductDto> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching products for page: {}", pageNo);
        return iProductService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<ProductDto> getProductByCode(@PathVariable String code) {
        log.info("Fetching product for code: {}", code);
        return iProductService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
