package com.mohaymen.store.service;

import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentSucceededEvent;

public interface IOrderPaymentService {

    void handlePaymentSuccess(PaymentSucceededEvent event);

    void handlePaymentFailure(PaymentFailedEvent event);
}
