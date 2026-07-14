package com.mohaymen.store.web;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Validated
@ConfigurationProperties(prefix = "store")
public record ApplicationProperties(String paymentServiceUrl) {
}
