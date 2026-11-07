package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.response.OrderResponse;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface IOrderService {

    OrderResponse checkout(Authentication authentication, String scenario);

    OrderResponse getOrder(Authentication authentication, String orderNumber);

    List<OrderResponse> getAllOrders(Authentication authentication);
}
