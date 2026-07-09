package com.mohaymen.store.service.impl;

import com.mohaymen.store.repository.ProductRepository;
import com.mohaymen.store.service.IProductService;
import com.mohaymen.store.web.ApplicationProperties;
import com.mohaymen.store.web.mapper.ProductMapper;
import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;
    private final ProductMapper productMapper;

    public PageResult<ProductDto> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<ProductDto> productsPage = productRepository.findAll(pageable).map(productMapper::toDto);

        return new PageResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }

    @Cacheable(value = "products", key = "#code")
    public Optional<ProductDto> getProductByCode(String code) {
        log.info("Fetching from database...");
        return productRepository.findByCode(code).map(productMapper::toDto);
    }
}
