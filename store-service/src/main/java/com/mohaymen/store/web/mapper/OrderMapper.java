package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderItem;
import com.mohaymen.store.web.response.OrderItemResponse;
import com.mohaymen.store.web.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toDto(Order order);
    List<OrderResponse> toDto(List<Order> orders);

    Order toEntity(OrderResponse dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponse toOrderItemDto(OrderItem item);
}
