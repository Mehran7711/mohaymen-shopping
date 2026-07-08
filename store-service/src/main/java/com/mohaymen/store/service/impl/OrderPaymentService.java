package com.mohaymen.store.service.impl;

import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderItem;
import com.mohaymen.store.model.OrderStatus;
import com.mohaymen.store.model.ProductInventory;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.IOrderPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

@Service
@RequiredArgsConstructor
@Transactional
public class OrderPaymentService implements IOrderPaymentService {


    private final OrderRepository orderRepository;

    private final ProductInventoryRepository inventoryRepository;

    @Override
    public void handlePaymentSuccess(PaymentSucceededEvent event) {

        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            return;
        }

        order.setStatus(OrderStatus.CONFIRMED);

        for (OrderItem item : order.getItems()) {
            ProductInventory inventory = inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
        }
    }

    @Override
    public void handlePaymentFailure(PaymentFailedEvent event) {

        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();

        if (order.getStatus() == OrderStatus.CANCELED) {
            return;
        }

        order.setStatus(OrderStatus.CANCELED);

        for (OrderItem item : order.getItems()) {
            ProductInventory inventory = inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
        }
    }
}
