package com.mohaymen.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mohaymen.store.model.Order;
import com.mohaymen.store.web.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse checkout(Long customerId, String scenario) throws JsonProcessingException;

    List<Order> getOrder(Long customerId);
}
