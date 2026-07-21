package com.mohaymen.norification.events;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.OrderCreatedEvent;
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
public class OrderCreatedConsumer {

    private final INotificationService notificationService;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "notification-service")
    public void consume(OrderCreatedEvent event) {

//        log.info("Received order event in notification-service: {}", event);
//        notificationService.sendOrderCreatedNotification(event);

        try {
            log.info("Received order event in notification-service: {}", event);
            notificationService.sendOrderCreatedNotification(event);
        } catch (Exception e) {
            log.error("Error processing order event: {}", event, e);
            throw e; // Re-throw if you want the listener to handle it
        }
    }
}
