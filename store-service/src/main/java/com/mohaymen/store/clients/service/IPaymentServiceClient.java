package com.mohaymen.store.clients.service;

import com.mohaymen.store.clients.filter.PaymentFilter;
import com.mohaymen.store.clients.response.PaymentApiResult;
import java.util.List;

public interface IPaymentServiceClient {
    List<PaymentApiResult> getAllPayments(PaymentFilter filter);
}
