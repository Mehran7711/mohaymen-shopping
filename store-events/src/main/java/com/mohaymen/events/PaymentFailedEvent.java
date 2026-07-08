package com.mohaymen.events;

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
public class PaymentFailedEvent extends BaseEvent {

    private Long orderId;

    private String reason;

    public PaymentFailedEvent(
            Long orderId,
            String reason) {

        super(EventType.PAYMENT_FAILED);

        this.orderId = orderId;
        this.reason = reason;
    }
}