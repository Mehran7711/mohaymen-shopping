package com.mohaymen.store.repository;

import com.mohaymen.store.model.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerId(String customerId);
}
