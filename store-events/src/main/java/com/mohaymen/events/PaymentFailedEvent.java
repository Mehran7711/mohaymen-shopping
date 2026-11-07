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
    private String customerName;
    private String email;

    public PaymentFailedEvent(Long orderId, String reason, String customerName, String email, String scenario) {

        super(EventType.PAYMENT_FAILED, scenario);

        this.orderId = orderId;
        this.customerName = customerName;
        this.email = email;
        this.reason = reason;
    }
}
