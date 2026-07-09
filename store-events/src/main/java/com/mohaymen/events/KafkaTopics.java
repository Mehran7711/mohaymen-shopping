package com.mohaymen.events;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String ORDER_CREATED = "order-created";

    public static final String PAYMENT_SUCCEEDED = "payment-succeeded";

    public static final String PAYMENT_FAILED = "payment-failed";
}
