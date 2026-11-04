package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Product;
import com.mohaymen.store.model.UserInfo;
import com.mohaymen.store.service.IInventoryService;
import com.mohaymen.store.service.ISecurityService;
import com.mohaymen.store.service.impl.InventoryService;
import com.mohaymen.store.web.response.ProductDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "quantity", expression = "java(getQuantity(product.getId(), inventoryService))")
    ProductDto toDto(Product product, @Context IInventoryService inventoryService);

    Product toEntity(ProductDto dto);

    default Long getQuantity(Long productId, InventoryService inventoryService) {
        return inventoryService.getAvailableStock(productId);
    }
}
