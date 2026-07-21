package com.mohaymen.store.service.impl;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.store.model.*;
import com.mohaymen.store.repository.CartRepository;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.OutboxRepository;
import com.mohaymen.store.service.IInventoryService;
import com.mohaymen.store.service.IKafkaProducerService;
import com.mohaymen.store.service.IOrderService;
import com.mohaymen.store.service.ISecurityService;
import com.mohaymen.store.web.mapper.OrderMapper;
import com.mohaymen.store.web.response.OrderResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import tools.jackson.databind.ObjectMapper;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final ObjectMapper objectMapper;
    private final IKafkaProducerService kafkaProducerService;
    private final IInventoryService inventoryService;
    private final OutboxRepository outboxRepository;
    private final ISecurityService securityService;

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // TODO OUTBOX_PATTERN

    //    @Transactional
    //    public OrderResponse checkout(Long customerId, String scenario) {
    //
    //        Cart cart = loadCart(customerId);
    //
    //        validateCart(cart);
    //
    //        Order order = createOrder(customerId);
    //
    //        BigDecimal total = BigDecimal.ZERO;
    //
    //        for (CartItem item : cart.getItems()) {
    //            inventoryService.reserveStock(item.getProduct(), item.getQuantity());
    //            OrderItem orderItem = createOrderItem(order, item);
    //            total = total.add(orderItem.getTotalPrice());
    //        }
    //
    //        order.setTotalAmount(total);
    //        orderRepository.save(order);
    //
    //        OrderCreatedEvent event = new OrderCreatedEvent(
    //                order.getId(),
    //                order.getOrderNumber(),
    //                order.getCustomerId(),
    //                order.getTotalAmount(),
    //                scenario);
    //
    //        String payload =
    //                objectMapper.writeValueAsString(event);
    //
    //        OutboxEvent outbox =
    //                OutboxEvent.builder()
    //                        .aggregateId(order.getId())
    //                        .aggregateType("ORDER")
    //                        .eventType(EventType.ORDER_CREATED)
    //                        .topic(KafkaTopics.ORDER_CREATED)
    //                        .payload(payload)
    //                        .status(OutboxStatus.NEW)
    //                        .retryCount(0)
    //                        .createdAt(LocalDateTime.now())
    //                        .build();
    //
    //        outboxRepository.save(outbox);
    //
    //        clearCart(cart);
    //        log.info("cart has been cleared. order: {} ", order.getId());
    //
    //        return orderMapper.toDto(order);
    //    }

    @Transactional
    public OrderResponse checkout(String scenario) {
        String customerId = securityService.getLoginUserId();

        Cart cart = loadCart(customerId);

        validateCart(cart);

        Order order = createOrder(customerId);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            inventoryService.reserveStock(item.getProduct(), item.getQuantity());
            OrderItem orderItem = createOrderItem(order, item);
            total = total.add(orderItem.getTotalPrice());
        }

        order.setTotalAmount(total);
        orderRepository.save(order);

        // TODO using Outbox Pattern later
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                log.info("Order has been updated-> going to publish order: {} ", order.getId());

                order.setStatus(OrderStatus.AWAITING_PAYMENT);
                publishOrderCreated(order, scenario);
            }
        });

        clearCart(cart);
        log.info("cart has been cleared. order: {} ", order.getId());

        return orderMapper.toDto(order, securityService);
    }

    private void publishOrderCreated(Order order, String scenario) {
        UserInfo userInfo = securityService.getCurrentUser(securityService.getLoginUserId());

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerId(),
                order.getTotalAmount(),
                userInfo.firstName() + ' ' + userInfo.lastName(),
                userInfo.email(),
                scenario);

        kafkaProducerService.publish(
                KafkaTopics.ORDER_CREATED, event.getOrderId().toString(), event);
    }

    @Override
    public List<Order> getOrder() {
        String customerId = securityService.getLoginUserId();
        return orderRepository.findByCustomerIdOrderByIdDesc(customerId);
    }

    private Cart loadCart(String customerId) {
        return cartRepository.findByCustomerId(customerId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private void clearCart(Cart cart) {
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private OrderItem createOrderItem(Order order, CartItem item) {
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();

        order.getItems().add(orderItem);
        return orderItem;
    }

    private Order createOrder(String customerId) {
        return Order.builder()
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .customerId(customerId)
                .build();
    }

    private void validateCart(Cart cart) {
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }
    }
}
