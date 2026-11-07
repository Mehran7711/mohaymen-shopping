package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.PageResult;
import com.mohaymen.store.webapp.web.response.ProductDto;
import org.springframework.security.core.Authentication;

public interface IProductService {
    void addProduct(Authentication authentication, AddCartItemRequest request);

    PageResult<ProductDto> loadProducts(Authentication authentication, Integer pageNo);
}
