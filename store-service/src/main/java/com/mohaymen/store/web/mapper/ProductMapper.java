package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Product;
import com.mohaymen.store.web.response.ProductDto;
import org.mapstruct.Mapper;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto dto);
}
