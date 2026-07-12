package com.mohaymen.store.service;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
public interface IKafkaProducerService {
    void publish(String topic, String key, Object event);
}
