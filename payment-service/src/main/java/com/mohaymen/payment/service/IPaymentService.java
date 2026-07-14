package com.mohaymen.payment.service;

import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.refund.RefundRequestedEvent;
import com.mohaymen.payment.model.Payment;
import com.mohaymen.payment.web.filter.PaymentFilter;
import com.mohaymen.payment.web.response.PaymentResult;
import com.mohaymen.payment.web.response.RefundResult;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

public interface IPaymentService {

    PaymentResult pay(String orderNumber, BigDecimal amount, String scenario);

    void processPayment(OrderCreatedEvent event);

    void refund(RefundRequestedEvent event);

    RefundResult doRefund(String transactionId, BigDecimal amount);

    Page<Payment> getPayments(int pageNo);

    List<Payment> getPaymentList(PaymentFilter filter);
}
