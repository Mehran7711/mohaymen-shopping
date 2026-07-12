package com.mohaymen.payment.service;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
public interface IPaymentKafkaProducer {

    /* run Kafka message transactional
    * kafkaTemplate.executeInTransaction(ops -> {
        ops.send(...);
        return true;
    });
    * */

    void publish(String topic, String key, Object event);
}
