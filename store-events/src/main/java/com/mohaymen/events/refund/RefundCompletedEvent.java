package com.mohaymen.events.refund;

import com.mohaymen.events.BaseEvent;
import com.mohaymen.events.EventType;
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
public class RefundCompletedEvent extends BaseEvent {

    private Long orderId;
    private String refundTransactionId;

    public RefundCompletedEvent(Long orderId, String refundTransactionId, String scenario) {
        super(EventType.REFUND_COMPLETED, scenario);
        this.orderId = orderId;
        this.refundTransactionId = refundTransactionId;
    }
}
