package com.mohaymen.store.repository;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdOrderByIdDesc(String customerId);

    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime createdAt);

    Order findByOrderNumber(String orderNumber);
}
