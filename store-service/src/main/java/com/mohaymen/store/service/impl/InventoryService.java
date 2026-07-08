package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.ProductInventory;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */
@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {

    private final ProductInventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductInventory getInventory(Long productId) {

        return inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public void addStock(Long productId, long quantity) {

        ProductInventory inventory = inventoryRepository
                .lockByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setQuantity(inventory.getQuantity() + quantity);
    }

    @Override
    public void reserveStock(Long productId, long quantity) {

        ProductInventory inventory = inventoryRepository
                .lockByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        long available = inventory.getQuantity() - inventory.getReservedQuantity();

        if (available < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
    }

    @Override
    public void releaseReservation(Long productId, long quantity) {

        ProductInventory inventory = inventoryRepository
                .lockByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        long reserved = inventory.getReservedQuantity();

        if (reserved < quantity) {
            throw new RuntimeException("Invalid reservation release");
        }

        inventory.setReservedQuantity(reserved - quantity);
    }

    @Override
    public void deductStock(Long productId, long quantity) {

        ProductInventory inventory = inventoryRepository
                .lockByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getReservedQuantity() < quantity) {
            throw new RuntimeException("Reserved quantity is too low");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
    }

    @Override
    @Transactional(readOnly = true)
    public long getAvailableStock(Long productId) {

        ProductInventory inventory = getInventory(productId);

        return inventory.getQuantity() - inventory.getReservedQuantity();
    }
}
