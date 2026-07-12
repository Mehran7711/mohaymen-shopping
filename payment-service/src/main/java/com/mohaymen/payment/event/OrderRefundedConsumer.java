package com.mohaymen.payment.event;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.refund.RefundRequestedEvent;
import com.mohaymen.payment.service.IPaymentService;
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
public class OrderRefundedConsumer {
    private final IPaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.REFUND_REQUESTED)
    public void refund(RefundRequestedEvent event) {
        paymentService.refund(event);
    }
}
