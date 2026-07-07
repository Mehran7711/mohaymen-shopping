package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.*;
import com.mohaymen.store.repository.CartRepository;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.IOrderService;
import com.mohaymen.store.web.ApplicationProperties;
import com.mohaymen.store.web.mapper.OrderMapper;
import com.mohaymen.store.web.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Slf4j
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ApplicationProperties properties;
    private final OrderMapper orderMapper;
    private CartRepository cartRepository;
    private ProductInventoryRepository inventoryRepository;

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public OrderResponse checkout(Long customerId) {

        Cart cart = loadCart(customerId);

        validateCart(cart);

        Order order = createOrder(customerId);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            reserveInventory(item);
            OrderItem orderItem = createOrderItem(order, item);
            order.getItems().add(orderItem);
            total = total.add(orderItem.getTotalPrice());
        }

        order.setTotalAmount(total);

        orderRepository.save(order);

        clearCart(cart);

        return orderMapper.toDto(order);
    }

    @Override
    public Order getOrder(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    private Cart loadCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));
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
                .totalPrice(item.getUnitPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity())))
                .build();


        order.getItems().add(orderItem);
        return orderItem;
    }

    private void reserveInventory(CartItem item) {
        ProductInventory inventory = inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
        long available = inventory.getQuantity() - inventory.getReservedQuantity();

        if (available < item.getQuantity()) {
            throw new RuntimeException(item.getProduct().getName() + " has insufficient stock");
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());

        inventoryRepository.save(inventory);
    }

    private Order createOrder(Long customerId) {
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
