package com.mohaymen.store.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.mohaymen.store.TestcontainersConfiguration;
import com.mohaymen.store.model.Product;

import java.math.BigDecimal;
import java.util.List;

import com.mohaymen.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

// memory support
@DataJpaTest
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(TestcontainersConfiguration.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // This test repository layer.
    @Test
    void shouldGetAllProducts() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(20);
    }

        @Test
        void shouldGetProductByCode() {
            Product product = productRepository.findByCode("P301").orElseThrow();
            assertThat(product.getPrice()).isEqualTo(new BigDecimal("39.5"));
        }

        @Test
        void shouldReturnEmptyWhenProductCodeNotExists() {
            assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
        }
}
