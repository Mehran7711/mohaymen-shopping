package com.mohaymen.store.service;

import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import java.util.Optional;

public interface IProductService {
    PageResult<ProductDto> getProducts(int pageNo);

    Optional<ProductDto> getProductByCode(String code);
}
