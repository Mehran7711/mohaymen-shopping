package com.mohaymen.events;

import lombok.NoArgsConstructor;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
@NoArgsConstructor
public final class EventType {

    public static final String ORDER_CREATED = "ORDER_CREATED";
    public static final String PAYMENT_SUCCEEDED = "PAYMENT_SUCCEEDED";
    public static final String PAYMENT_FAILED = "PAYMENT_FAILED";
    public static final String REFUND_REQUESTED = "REFUND_REQUESTED";
    public static final String REFUND_COMPLETED = "REFUND_COMPLETED";
    public static final String REFUND_FAILED = "REFUND_FAILED";
}
