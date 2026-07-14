package com.mohaymen.store.web.controller;

import com.mohaymen.store.clients.filter.PaymentFilter;
import com.mohaymen.store.clients.response.PaymentApiResult;
import com.mohaymen.store.clients.service.impl.IPaymentServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */

@RestController
@RequestMapping("/api/client/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentClientController {
    private final IPaymentServiceClient iPaymentServiceClient;

    @PostMapping("/search")
    ResponseEntity<List<PaymentApiResult>> getProducts(@RequestBody PaymentFilter filter) {
        return new ResponseEntity<>(iPaymentServiceClient.getAllPayments(filter), HttpStatus.OK);
    }
}
