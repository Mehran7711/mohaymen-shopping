package com.mohaymen.norification.service;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.events.refund.RefundCompletedEvent;

public interface INotificationService {
    void sendOrderCreatedNotification(OrderCreatedEvent event);

    void sendOrderCancelledNotification(PaymentFailedEvent event);

    void sendOrderSuccessfulNotification(PaymentSucceededEvent event);

    void sendOrderRefundNotification(RefundCompletedEvent event);
}
