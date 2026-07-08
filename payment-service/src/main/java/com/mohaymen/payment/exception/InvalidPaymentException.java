package com.mohaymen.payment.exception;

/**
 * Author: Mehran
 * Created on: 7/2/26
 */
public class InvalidPaymentException extends RuntimeException {

    public InvalidPaymentException(String message) {
        super(message);
    }
}
