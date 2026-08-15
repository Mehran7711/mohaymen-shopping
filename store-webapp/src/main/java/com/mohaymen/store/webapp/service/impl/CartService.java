package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.ICartService;
import com.mohaymen.store.webapp.service.ISecurityClientService;
import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ISecurityClientService clientService;

    @Override
    public CartResponse getCart(Authentication authentication) {
        return clientService
                .get(authentication, "/store/api/cart")
                .retrieve()
                .body(CartResponse.class);
    }

    @Override
    public CartResponse updateQuantity(Authentication authentication,
                                       Long productId,
                                       AddCartItemRequest request) {

        return clientService
                .put(authentication, "/store/api/cart/items/" + productId)
                .body(request)
                .retrieve()
                .body(CartResponse.class);
    }

    @Override
    public void deleteItem(Authentication authentication, Long productId) {
        clientService
                .delete(authentication, "/store/api/cart/items/" + productId)
                .retrieve()
                .toBodilessEntity();
    }
}
