package com.mohaymen.store.web.mapper;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderItem;
import com.mohaymen.store.model.UserInfo;
import com.mohaymen.store.service.ISecurityService;
import com.mohaymen.store.web.response.OrderItemResponse;
import com.mohaymen.store.web.response.OrderResponse;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer", expression = "java(mapUser(order.getCustomerId(), securityService))")
    OrderResponse toDto(Order order, @Context ISecurityService securityService);

    List<OrderResponse> toDto(List<Order> orders, @Context ISecurityService securityService);

    Order toEntity(OrderResponse dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponse toOrderItemDto(OrderItem item);

    default UserInfo mapUser(String customerId, ISecurityService securityService) {
        return securityService.getCurrentUser(customerId);
    }
}
