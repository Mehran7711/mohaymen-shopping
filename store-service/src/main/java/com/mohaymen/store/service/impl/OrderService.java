package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.*;
import com.mohaymen.store.repository.CartRepository;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.ProductRepository;
import com.mohaymen.store.service.IOrderService;
import com.mohaymen.store.service.IProductService;
import com.mohaymen.store.web.ApplicationProperties;
import com.mohaymen.store.web.mapper.OrderMapper;
import com.mohaymen.store.web.mapper.ProductMapper;
import com.mohaymen.store.web.response.OrderDto;
import com.mohaymen.store.web.response.PageResult;
import com.mohaymen.store.web.response.ProductDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
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

    @Override
    public Page<OrderDto> getOrders(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public Order saveOrder(Long customerId) {

//        Cart cart = cartRepository.findByCustomerId(customerId)
//                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));
//
//        if (cart.getItems().isEmpty()) {
//            throw new RuntimeException("Shopping cart is empty");
//        }
//
//        Order order = new Order();
//        order.setOrderNumber(generateOrderNumber());
//        order.setCustomerId(customerId);
//        order.setStatus(OrderStatus.PENDING);
//        order.setCreatedAt(LocalDateTime.now());
//
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (CartItem cartItem : cart.getItems()) {
//
//            ProductInventory inventory = inventoryRepository
//                    .findById(cartItem.getProduct().getId())
//                    .orElseThrow();
//
//            long available =
//                    inventory.getQuantity() - inventory.getReservedQuantity();
//
//            if (available < cartItem.getQuantity()) {
//                throw new RuntimeException(
//                        "Not enough stock for product "
//                                + cartItem.getProduct().getCode());
//            }
//
//            // Reserve inventory
//            inventory.setReservedQuantity(
//                    inventory.getReservedQuantity() + cartItem.getQuantity());
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setProductId(cartItem.getProduct().getId());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setUnitPrice(cartItem.getUnitPrice());
//
//            BigDecimal lineTotal = cartItem.getUnitPrice()
//                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
//
//            orderItem.setTotalPrice(lineTotal);
//
//            order.getItems().add(orderItem);
//
//            total = total.add(lineTotal);
//        }
//
//        order.setTotalAmount(total);
//
//        return orderRepository.save(order);

        return null;
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
