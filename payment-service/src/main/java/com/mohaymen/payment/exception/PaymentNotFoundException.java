package com.mohaymen.payment.exception;

/**
 * Author: Mehran
 * Created on: 7/2/26
 */

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public static PaymentNotFoundException forCode(String code) {
        return new PaymentNotFoundException("Payment with code " + code + " not found");
    }
}