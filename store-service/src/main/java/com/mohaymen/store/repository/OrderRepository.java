package com.mohaymen.store.repository;

import com.mohaymen.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByCustomerId(Long customerId);
}
