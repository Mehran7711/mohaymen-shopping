package com.mohaymen.store.clients.service.impl;

import com.mohaymen.store.clients.filter.PaymentFilter;
import com.mohaymen.store.clients.response.PaymentApiResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */

@Component
@Slf4j
@AllArgsConstructor
public class PaymentServiceClient implements IPaymentServiceClient {

    private final RestClient restClient;


    //    @CircuitBreaker(name = "catalog-service")
//    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    @Override
    public List<PaymentApiResult> getAllPayments(PaymentFilter filter) {
        log.info("Fetching payment filter {}", filter);

        return restClient.post()
                .uri("/api/payments/search")
                .body(filter)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
