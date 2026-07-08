package com.mohaymen.payment.event;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.payment.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final IPaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "payment-service")
    public void consume(OrderCreatedEvent event) {

        log.info("Received order event {}", event);

        paymentService.processPayment(event);
    }
}
