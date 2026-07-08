package com.mohaymen.payment.model;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

public enum PaymentStatus {
    INITIATED,
    PROCESSING,
    COMPLETED,
    FAILED,
    REFUNDED,
    PARTIALLY_REFUNDED,
}