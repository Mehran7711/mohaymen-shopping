package com.mohaymen.store.repository;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
