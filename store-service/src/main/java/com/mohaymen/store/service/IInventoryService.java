package com.mohaymen.store.service;

import com.mohaymen.store.model.ProductInventory;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */

public interface IInventoryService {

    ProductInventory getInventory(Long productId);

    void addStock(Long productId, long quantity);

    void reserveStock(Long productId, long quantity);

    void releaseReservation(Long productId, long quantity);

    void deductStock(Long productId, long quantity);

    long getAvailableStock(Long productId);
}