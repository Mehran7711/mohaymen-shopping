package com.mohaymen.events;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor
public class PaymentSucceededEvent extends BaseEvent {

    private Long orderId;

    private String transactionId;

    private BigDecimal amount;

    public PaymentSucceededEvent(
            Long orderId,
            String transactionId,
            BigDecimal amount) {

        super(EventType.PAYMENT_SUCCEEDED);

        this.orderId = orderId;
        this.transactionId = transactionId;
        this.amount = amount;
    }
}