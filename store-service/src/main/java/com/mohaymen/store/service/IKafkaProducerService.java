package com.mohaymen.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

public interface IKafkaProducerService {
    void publish(String topic, Object event);
}
