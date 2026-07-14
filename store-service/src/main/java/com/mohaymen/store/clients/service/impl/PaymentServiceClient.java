package com.mohaymen.store.clients.service.impl;

import com.mohaymen.store.clients.filter.PaymentFilter;
import com.mohaymen.store.clients.response.PaymentApiResult;
import com.mohaymen.store.clients.service.IPaymentServiceClient;

import java.util.Collections;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */
@Component
@Slf4j
@AllArgsConstructor
public class PaymentServiceClient implements IPaymentServiceClient {

    private final RestClient restClient;

    @CircuitBreaker(name = "payment-service")
    @Retry(name = "payment-service", fallbackMethod = "getAllPaymentsFallback")
    @Override
    public List<PaymentApiResult> getAllPayments(PaymentFilter filter) {
        log.info("Fetching payment filter {}", filter);

        try {
            return restClient
                    .post()
                    .uri("/api/payments/search")
                    .body(filter)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            log.error("Error while fetching payment filter {}", filter, ex);
            throw new RuntimeException(ex);
        }
    }

    List<PaymentApiResult> getAllPaymentsFallback(PaymentFilter filter, Throwable ex) {
        log.info("payment-service get payments list by filter: {} ", filter);
        return Collections.emptyList();
    }
}
