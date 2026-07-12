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
public class RefundFailedEvent extends BaseEvent {
    private Long orderId;
    private String reason;

    public RefundFailedEvent(Long orderId, String reason, String scenario) {
        super(EventType.REFUND_FAILED, scenario);
        this.orderId = orderId;
        this.reason = reason;
    }
}
