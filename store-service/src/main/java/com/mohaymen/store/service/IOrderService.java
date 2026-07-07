package com.mohaymen.store.service;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    OrderResponse checkout(Long customerId);
    Order getOrder(Long customerId);
}
