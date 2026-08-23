package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.ICartService;
import com.mohaymen.store.webapp.service.IOrderService;
import com.mohaymen.store.webapp.service.ISecurityClientService;
import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.CartResponse;
import com.mohaymen.store.webapp.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final ISecurityClientService clientService;


    @Override
    public OrderResponse checkout(Authentication authentication, String scenario) {
        return clientService
                .post(authentication, "/store/api/order/checkout?scenario=" + scenario)
                .retrieve()
                .body(OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Authentication authentication, String orderNumber) {
        return clientService
                .get(authentication, "/store/api/order/" + orderNumber)
                .retrieve()
                .body(OrderResponse.class);
    }
}
