package com.mohaymen.store.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "store")
@Getter
@Setter
public class StoreProperties {

    private Integer timeToLiveSeconds;
    private Long maxEntries;


}
