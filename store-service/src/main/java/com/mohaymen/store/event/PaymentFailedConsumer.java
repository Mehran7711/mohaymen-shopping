package com.mohaymen.store.event;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.store.service.IOrderPaymentService;
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
public class PaymentFailedConsumer {

    private final IOrderPaymentService orderPaymentService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "order-service")
    public void consume(PaymentFailedEvent event) {
        log.info("Payment failed received for order {}", event.getOrderId());
        orderPaymentService.handlePaymentFailure(event);
    }
}
