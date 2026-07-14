package com.mohaymen.payment.web.mapper;

import com.mohaymen.payment.model.Payment;
import com.mohaymen.payment.web.response.PaymentApiResult;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentApiResult toDto(Payment payment);

    List<PaymentApiResult> toDto(List<Payment> payments);

    Payment toEntity(PaymentApiResult dto);
}
