package com.mohaymen.store.service;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.request.CartItemRequest;
import com.mohaymen.store.web.response.CartResponse;
import com.mohaymen.store.web.response.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICartService {

    CartResponse addProduct(CartItemRequest request);

    CartResponse updateQuantity(Long productId, CartItemRequest request);

    void removeProduct(Long customerId, Long productId);

    CartResponse getCart(Long customerId);
}
