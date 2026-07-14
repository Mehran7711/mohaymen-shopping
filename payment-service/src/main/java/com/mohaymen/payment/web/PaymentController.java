package com.mohaymen.payment.web;

import com.mohaymen.payment.service.IPaymentService;
import com.mohaymen.payment.web.filter.PaymentFilter;
import com.mohaymen.payment.web.mapper.PaymentMapper;
import com.mohaymen.payment.web.response.PaymentApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    Page<PaymentApiResult> getPayments(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching payments for page: {}", pageNo);
        return paymentService.getPayments(pageNo).map(paymentMapper::toDto);
    }

    @PostMapping("/search")
    List<PaymentApiResult> getPaymentList(@RequestBody PaymentFilter filter) {
        log.info("Fetching payments List");
        return paymentMapper.toDto(paymentService.getPaymentList(filter));
    }
}
