package com.mohaymen.store.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Author: Mehran
 * Created on: 7/4/26
 */
@Entity
@Table(name = "product_inventory")
@Getter
@Setter
@NoArgsConstructor
public class ProductInventory {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Version
    private Long version;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Long reservedQuantity = 0L;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
