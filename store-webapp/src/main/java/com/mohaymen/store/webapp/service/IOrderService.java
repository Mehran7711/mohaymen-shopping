package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.CartResponse;
import com.mohaymen.store.webapp.web.response.OrderResponse;
import org.springframework.security.core.Authentication;

public interface IOrderService {

    OrderResponse checkout(Authentication authentication, String scenario);

    OrderResponse getOrder(Authentication authentication, String orderNumber);

}
