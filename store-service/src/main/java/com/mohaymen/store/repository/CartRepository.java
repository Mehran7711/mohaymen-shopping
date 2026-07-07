package com.mohaymen.store.repository;

import com.mohaymen.store.model.Cart;
import com.mohaymen.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerId(Long customerId);
}
