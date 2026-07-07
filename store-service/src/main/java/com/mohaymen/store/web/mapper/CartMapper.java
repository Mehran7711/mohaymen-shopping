package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.response.OrderResponse;
import org.mapstruct.Mapper;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface CartMapper {

    OrderResponse toDto(Order order);

    Order toEntity(OrderResponse dto);
}
