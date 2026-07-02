package com.mohaymen.store.service;

import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface IProductService {
    PageResult<ProductDto> getProducts(int pageNo);

    @Cacheable(value = "products", key = "#code")
    Optional<ProductDto> getProductByCode(String code);
}
