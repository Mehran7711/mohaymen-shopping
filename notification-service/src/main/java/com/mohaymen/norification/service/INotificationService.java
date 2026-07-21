package com.mohaymen.norification.service;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;

public interface INotificationService {
    void sendOrderCreatedNotification(OrderCreatedEvent event);

    void sendOrderCancelledNotification(PaymentFailedEvent event);

    void sendOrderSuccessfulNotification(PaymentSucceededEvent event);
}
