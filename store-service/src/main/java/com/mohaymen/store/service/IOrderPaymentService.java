package com.mohaymen.store.service;

import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.events.refund.RefundCompletedEvent;
import com.mohaymen.store.model.OutboxEvent;

public interface IOrderPaymentService {

    void handlePaymentSuccess(PaymentSucceededEvent event);

    void handlePaymentFailure(PaymentFailedEvent event);

    void refundCompleted(RefundCompletedEvent event);

    void compensateOrder(OutboxEvent outbox);
}
