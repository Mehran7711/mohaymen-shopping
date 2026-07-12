package com.mohaymen.store.service;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.response.OrderResponse;

public interface IOrderService {
    OrderResponse checkout(Long customerId, String scenario);

    Order getOrder(Long customerId);
}
