package com.mohaymen.store.service;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.response.OrderDto;
import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IOrderService {
    Page<OrderDto> getOrders(Pageable pageable);
    Order saveOrder(Long customerId);
}
