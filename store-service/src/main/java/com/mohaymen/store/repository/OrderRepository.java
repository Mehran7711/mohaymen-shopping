package com.mohaymen.store.repository;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByCustomerId(Long customerId);

    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime createdAt);
}
