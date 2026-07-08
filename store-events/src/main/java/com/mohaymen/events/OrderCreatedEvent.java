package com.mohaymen.events;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderCreatedEvent extends BaseEvent {

    private Long orderId;

    private String orderNumber;

    private Long customerId;

    private BigDecimal amount;

    public OrderCreatedEvent(
            Long orderId,
            String orderNumber,
            Long customerId,
            BigDecimal amount) {

        super(EventType.ORDER_CREATED);

        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.amount = amount;
    }
}