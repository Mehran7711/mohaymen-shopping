package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.ProductDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IProductService {
    void addProduct(Authentication authentication, AddCartItemRequest request);
    List<ProductDto> loadProducts(Authentication authentication, Integer pageNo);
}
