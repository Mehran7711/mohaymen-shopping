package com.mohaymen.payment.service;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.refund.RefundRequestedEvent;
import com.mohaymen.payment.web.response.PaymentResult;
import com.mohaymen.payment.web.response.RefundResult;
import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResult pay(String orderNumber, BigDecimal amount, String scenario);

    void processPayment(OrderCreatedEvent event);

    void refund(RefundRequestedEvent event);

    RefundResult doRefund(String transactionId, BigDecimal amount);
}
