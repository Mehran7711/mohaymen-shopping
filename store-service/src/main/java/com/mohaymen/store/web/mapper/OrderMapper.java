package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.Product;
import com.mohaymen.store.web.response.OrderDto;
import com.mohaymen.store.web.response.ProductDto;
import org.mapstruct.Mapper;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);
}
