package com.mohaymen.payment.service.impl;

import com.mohaymen.events.KafkaTopics;
import com.mohaymen.events.OrderCreatedEvent;
import com.mohaymen.events.PaymentFailedEvent;
import com.mohaymen.events.PaymentScenario;
import com.mohaymen.events.PaymentSucceededEvent;
import com.mohaymen.events.refund.RefundCompletedEvent;
import com.mohaymen.events.refund.RefundRequestedEvent;
import com.mohaymen.payment.model.Payment;
import com.mohaymen.payment.model.PaymentStatus;
import com.mohaymen.payment.repository.PaymentRepository;
import com.mohaymen.payment.repository.PaymentSpecification;
import com.mohaymen.payment.service.IPaymentKafkaProducer;
import com.mohaymen.payment.service.IPaymentService;
import com.mohaymen.payment.web.ApplicationProperties;
import com.mohaymen.payment.web.filter.PaymentFilter;
import com.mohaymen.payment.web.response.PaymentResult;
import com.mohaymen.payment.web.response.RefundResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final IPaymentKafkaProducer producer;
    private final ApplicationProperties properties;

    @Override
    public PaymentResult pay(String orderNumber, BigDecimal amount, String scenario) {

        boolean failed = Objects.equals(scenario, PaymentScenario.PAYMENT_FAILED.name());

        if (failed) {
            return new PaymentResult(false, null, "Insufficient funds");
        }
        return new PaymentResult(true, UUID.randomUUID().toString(), null);
    }

    @Override
    public void processPayment(OrderCreatedEvent event) {

        if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
            return;
        }

        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .orderNumber(event.getOrderNumber())
                .customerId(event.getCustomerId())
                .amount(event.getAmount())
                .status(PaymentStatus.INITIATED)
                .build();

        paymentRepository.save(payment);

        PaymentResult result = pay(event.getOrderNumber(), event.getAmount(), event.getScenario());

        if (result.success()) {

            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setTransactionId(result.transactionId());
            paymentRepository.save(payment);
            producer.publish(
                    KafkaTopics.PAYMENT_SUCCEEDED,
                    event.getOrderId().toString(),
                    new PaymentSucceededEvent(
                            event.getOrderId(), result.transactionId(), event.getAmount(), event.getScenario()));

        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason(result.reason());
            paymentRepository.save(payment);
            producer.publish(
                    KafkaTopics.PAYMENT_FAILED,
                    event.getOrderId().toString(),
                    new PaymentFailedEvent(event.getOrderId(),
                            result.reason(),
                            event.getCustomerName(),
                            event.getEmail(),
                            event.getScenario()));
        }
    }

    @Override
    public void refund(RefundRequestedEvent event) {
        log.info("Payment Refund Running for order: {}", event.getOrderId());
        Payment payment = paymentRepository.findByOrderId(event.getOrderId()).orElseThrow();

        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            return;
        }

        RefundResult result = doRefund(payment.getTransactionId(), event.getAmount());

        if (result.success()) {
            payment.setStatus(PaymentStatus.REFUNDED);
            producer.publish(
                    KafkaTopics.REFUND_COMPLETED,
                    event.getOrderId().toString(),
                    new RefundCompletedEvent(event.getOrderId(), result.transactionId(), event.getScenario()));
        }
    }

    @Override
    public RefundResult doRefund(String transactionId, BigDecimal amount) {
        return new RefundResult(true, transactionId, amount);
    }

    @Override
    public Page<Payment> getPayments(int pageNo) {
        Sort sort = Sort.by("id").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        return paymentRepository.findAll(pageable);
    }

    @Override
    public List<Payment> getPaymentList(PaymentFilter filter) {
        log.info("Fetching payment List");
        //        sleep(6);
        return paymentRepository.findAll(PaymentSpecification.build(filter));
    }

    void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
        }
    }
}
