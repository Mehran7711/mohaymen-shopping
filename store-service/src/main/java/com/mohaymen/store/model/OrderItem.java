package com.mohaymen.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/4/26
 */

@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
}
