package com.mohaymen.store.service.impl;

import com.mohaymen.store.repository.ProductRepository;
import com.mohaymen.store.service.IProductService;
import com.mohaymen.store.web.ApplicationProperties;
import com.mohaymen.store.web.mapper.ProductMapper;
import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;
    private final ProductMapper productMapper;

    ProductService(ProductRepository productRepository, ApplicationProperties properties, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.properties = properties;
        this.productMapper = productMapper;
    }

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

    public Optional<ProductDto> getProductByCode(String code) {
        return productRepository.findByCode(code).map(productMapper::toDto);
    }
}
