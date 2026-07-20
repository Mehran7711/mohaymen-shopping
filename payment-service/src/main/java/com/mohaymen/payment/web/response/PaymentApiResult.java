package com.mohaymen.payment.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/13/26
 */
public record PaymentApiResult(
        Long orderId, String orderNumber, String customerId, BigDecimal amount, String transactionId) {}
