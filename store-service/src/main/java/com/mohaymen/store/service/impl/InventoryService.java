package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.Product;
import com.mohaymen.store.model.ProductInventory;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {

    private final ProductInventoryRepository inventoryRepository;
    private final RedissonClient redissonClient;

    @Override
    @Transactional(readOnly = true)
    public ProductInventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public void addStock(Long productId, long quantity) {
        ProductInventory inventory = inventoryRepository.lockByProductId(productId).orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setQuantity(inventory.getQuantity() + quantity);
    }

    @Override
    public void reserveStock(Product product, long quantity) {
        ProductInventory inventory = inventoryRepository.lockByProductId(product.getId()).orElseThrow(() -> new RuntimeException("Inventory not found"));
        long available = inventory.getQuantity() - inventory.getReservedQuantity();
        if (available < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
    }

    @Override
    public void releaseReservation(Product product, long quantity) {

        ProductInventory inventory = inventoryRepository.lockByProductId(product.getId()).orElseThrow(() -> new RuntimeException("Inventory not found"));
        long reserved = inventory.getReservedQuantity();
        if (reserved < quantity) {
            throw new RuntimeException("Invalid reservation release");
        }
        inventory.setReservedQuantity(reserved - quantity);
    }

    @Override
    public void deductStock(Product product, long quantity) {

        ProductInventory inventory = inventoryRepository.lockByProductId(product.getId()).orElseThrow(() -> new RuntimeException("Inventory not found"));

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

//    @Override
//    public void reserveStock(Product product, long quantity) {
//
//        executeWithLock(product.getId(), () -> {
//            ProductInventory inventory = inventoryRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//            long available = inventory.getQuantity() - inventory.getReservedQuantity();
//
//            if (available < quantity) {
//                throw new RuntimeException("Insufficient stock");
//            }
//
//            inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
//            return null;
//        });
//    }


    private RLock getProductLock(Long productId) {
        return redissonClient.getLock("inventory:lock:" + productId);
    }


    private <T> void executeWithLock(Long productId, Supplier<T> action) {

        RLock lock = getProductLock(productId);
        boolean acquired = false;

        try {
            /*boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);*/
            acquired = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (!acquired) {
                throw new RuntimeException("Could not acquire inventory lock");
            }
            action.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
