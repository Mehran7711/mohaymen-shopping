package com.mohaymen.events;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEvent {

    private String eventId;

    private LocalDateTime createdAt;

    private String eventType;

    protected BaseEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.eventType = eventType;
    }
}