package com.mohaymen.events;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEvent {

    private String eventId;
    private LocalDateTime createdAt;
    private String eventType;
    private String scenario;

    protected BaseEvent(String eventType, String scenario) {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.eventType = eventType;
        this.scenario = scenario;
    }
}
