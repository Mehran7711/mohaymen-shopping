package com.mohaymen.store.service;

import com.mohaymen.store.web.request.CartItemRequest;
import com.mohaymen.store.web.response.CartResponse;

public interface ICartService {

    CartResponse addProduct(CartItemRequest request);

    CartResponse updateQuantity(Long productId, CartItemRequest request);

    void removeProduct(Long productId);

    CartResponse getCart();
}
