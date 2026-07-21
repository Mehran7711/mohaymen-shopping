package com.mohaymen.norification.events;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.norification.service.INotificationService;
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

    private final INotificationService notificationService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED, groupId = "notification-service")
    public void consume(PaymentFailedEvent event) {
        log.info("Received PaymentFailedEvent in notification-service: {}", event.getOrderId());
        notificationService.sendOrderCancelledNotification(event);
    }
}
