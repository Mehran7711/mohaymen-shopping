package com.mohaymen.payment.web.response;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

public record PaymentResult(
        boolean success,
        String transactionId,
        String reason) {
}
