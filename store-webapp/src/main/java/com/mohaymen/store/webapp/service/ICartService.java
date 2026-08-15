package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.CartResponse;
import org.springframework.security.core.Authentication;

public interface ICartService {
    CartResponse getCart(Authentication authentication);

    CartResponse updateQuantity(Authentication authentication,
                                Long productId,
                                AddCartItemRequest request);

    void deleteItem(Authentication authentication,
                            Long productId);
}
