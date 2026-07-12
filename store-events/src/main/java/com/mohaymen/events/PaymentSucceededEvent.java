package com.mohaymen.events;

import java.math.BigDecimal;
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

    public PaymentSucceededEvent(Long orderId, String transactionId, BigDecimal amount, String scenario) {

        super(EventType.PAYMENT_SUCCEEDED, scenario);

        this.orderId = orderId;
        this.transactionId = transactionId;
        this.amount = amount;
    }
}
