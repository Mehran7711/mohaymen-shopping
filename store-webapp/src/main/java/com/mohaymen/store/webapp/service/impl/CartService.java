package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.ICartService;
import com.mohaymen.store.webapp.service.ISecurityClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ISecurityClientService clientService;

    @Override
    public String getCart(Authentication authentication) {
        return clientService
                .get(authentication, "/store/api/cart")
                .retrieve()
                .body(String.class);
    }
}
