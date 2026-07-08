package com.mohaymen.store.model;

/**
 * Author: Mehran
 * Created on: 7/4/26
 */
public enum OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    CONFIRMED,
    PROCESSING,
    SHIPPING,
    DELIVERED,
    CANCELED,
    REFUNDED
}
