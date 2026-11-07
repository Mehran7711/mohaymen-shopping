package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.IProductService;
import com.mohaymen.store.webapp.service.ISecurityClientService;
import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.PageResult;
import com.mohaymen.store.webapp.web.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ISecurityClientService clientService;

    @Override
    public void addProduct(Authentication authentication, AddCartItemRequest request) {
        clientService
                .post(authentication, "/store/api/cart/items")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public PageResult<ProductDto> loadProducts(Authentication authentication, Integer pageNo) {
        return clientService
                .get(authentication, "/store/api/products?page=" + pageNo)
                .retrieve()
                .body(new ParameterizedTypeReference<PageResult<ProductDto>>() {});
    }
}
