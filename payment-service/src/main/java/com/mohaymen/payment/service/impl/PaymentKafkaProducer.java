package com.mohaymen.payment.service.impl;

import com.mohaymen.payment.service.IPaymentKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentKafkaProducer implements IPaymentKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(String topic, String key, Object event) {

        kafkaTemplate.send(topic, key, event).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish event to topic {}", topic, ex);
            } else {
                log.info(
                        "Event published to topic={} offset={} key={}",
                        topic,
                        result.getRecordMetadata().offset(),
                        key);
            }
        });
    }
}
