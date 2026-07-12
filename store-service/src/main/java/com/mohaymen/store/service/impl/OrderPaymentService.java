package com.mohaymen.store.service.impl;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentScenario;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.events.refund.RefundCompletedEvent;
import com.mohaymen.events.refund.RefundRequestedEvent;
import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderItem;
import com.mohaymen.store.model.OrderStatus;
import com.mohaymen.store.model.ProductInventory;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.IKafkaProducerService;
import com.mohaymen.store.service.IOrderPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderPaymentService implements IOrderPaymentService {

    private final OrderRepository orderRepository;
    private final ProductInventoryRepository inventoryRepository;
    private final IKafkaProducerService kafkaProducerService;

    @Override
    public void handlePaymentSuccess(PaymentSucceededEvent event) {

        try {

            if (event.getScenario().equals(PaymentScenario.ORDER_FAILED.name())) {
                throw new Exception(" **** Order Update Failed, Do refund ****");
            }

            Order order = orderRepository.findById(event.getOrderId()).orElseThrow();

            if (order.getStatus() == OrderStatus.CONFIRMED) {
                return;
            }

            order.setStatus(OrderStatus.CONFIRMED);

            for (OrderItem item : order.getItems()) {
                ProductInventory inventory =
                        inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
                inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
            }
        } catch (Exception ex) {

            log.error("ORDER_FAILED_ {}", ex.getMessage());

            Order order = orderRepository.findById(event.getOrderId()).orElseThrow();

            order.setStatus(OrderStatus.REFUND_PENDING);

            kafkaProducerService.publish(
                    KafkaTopics.REFUND_REQUESTED,
                    event.getOrderId().toString(),
                    new RefundRequestedEvent(
                            event.getOrderId(),
                            event.getTransactionId(),
                            event.getAmount(),
                            ex.getMessage(),
                            event.getScenario()));
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
            ProductInventory inventory =
                    inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
        }
    }

    @Override
    public void refundCompleted(RefundCompletedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();

        if (order.getStatus() == OrderStatus.REFUNDED) {
            return;
        }
        order.setStatus(OrderStatus.REFUNDED);
        for (OrderItem item : order.getItems()) {
            ProductInventory inventory =
                    inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
        }
    }
}
