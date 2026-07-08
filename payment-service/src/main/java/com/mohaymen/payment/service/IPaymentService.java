package com.mohaymen.payment.service;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.payment.web.response.PaymentResult;
import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResult pay(String orderNumber, BigDecimal amount);

    void processPayment(OrderCreatedEvent event);
}
