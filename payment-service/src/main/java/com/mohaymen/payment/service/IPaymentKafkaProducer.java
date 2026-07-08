package com.mohaymen.payment.service;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */


public interface IPaymentKafkaProducer {
    void publish(String topic, Object event);
}
