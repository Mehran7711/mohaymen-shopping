package com.mohaymen.events;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderCreatedEvent extends BaseEvent {

    private Long orderId;

    private String orderNumber;

    private String customerId;

    private BigDecimal amount;

    public OrderCreatedEvent(Long orderId, String orderNumber, String customerId, BigDecimal amount, String scenario) {

        super(EventType.ORDER_CREATED, scenario);

        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.amount = amount;
    }
}
