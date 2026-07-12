package com.mohaymen.store.event;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.refund.RefundCompletedEvent;
import com.mohaymen.store.service.IOrderPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author: Mehran
 * Created on: 7/11/26
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RefundCompletedConsumer {

    private final IOrderPaymentService orderPaymentService;

    @KafkaListener(topics = KafkaTopics.REFUND_COMPLETED)
    public void refundCompleted(RefundCompletedEvent event) {
        log.info("Refund success for order {}", event.getOrderId());
        orderPaymentService.refundCompleted(event);
    }
}
