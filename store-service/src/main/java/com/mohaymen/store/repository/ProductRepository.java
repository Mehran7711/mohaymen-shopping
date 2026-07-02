package com.mohaymen.store.repository;

import com.mohaymen.store.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}
