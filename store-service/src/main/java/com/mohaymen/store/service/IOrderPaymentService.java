package com.mohaymen.store.service;

import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.events.refund.RefundCompletedEvent;

public interface IOrderPaymentService {

    void handlePaymentSuccess(PaymentSucceededEvent event);

    void handlePaymentFailure(PaymentFailedEvent event);

    void refundCompleted(RefundCompletedEvent event);
}
