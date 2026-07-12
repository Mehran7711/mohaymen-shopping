package com.mohaymen.store.service;

import com.mohaymen.store.model.Product;
import com.mohaymen.store.model.ProductInventory;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */
public interface IInventoryService {

    ProductInventory getInventory(Long productId);

    void addStock(Long productId, long quantity);

    void reserveStock(Product product, long quantity);

    void releaseReservation(Product product, long quantity);

    void deductStock(Product product, long quantity);

    long getAvailableStock(Long productId);
}
