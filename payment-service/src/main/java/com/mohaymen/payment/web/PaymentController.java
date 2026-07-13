package com.mohaymen.payment.web;

import com.mohaymen.payment.service.IPaymentService;
import com.mohaymen.payment.web.mapper.PaymentMapper;
import com.mohaymen.payment.web.response.PaymentApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Mehran
 * Created on: 7/13/26
 */
@RestController
@RequestMapping("/api/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @GetMapping
    Page<PaymentApiResult> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching payments for page: {}", pageNo);
        return paymentService.getPayments(pageNo).map(paymentMapper::toDto);
    }
}
