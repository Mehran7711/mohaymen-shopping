package com.mohaymen.events.refund;

import com.mohaymen.events.BaseEvent;
import com.mohaymen.events.EventType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Author: Mehran
 * Created on: 7/11/26
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class RefundRequestedEvent extends BaseEvent {
    private Long orderId;
    private String transactionId;
    private BigDecimal amount;
    private String reason;

    public RefundRequestedEvent(Long orderId, String transactionId, BigDecimal amount, String reason, String scenario) {
        super(EventType.REFUND_REQUESTED, scenario);
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.reason = reason;
    }
}
