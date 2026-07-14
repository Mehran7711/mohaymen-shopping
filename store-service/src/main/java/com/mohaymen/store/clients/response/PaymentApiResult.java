package com.mohaymen.store.clients.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/13/26
 */
public record PaymentApiResult(
        Long orderId, String orderNumber, Long customerId, BigDecimal amount, String transactionId) {}
