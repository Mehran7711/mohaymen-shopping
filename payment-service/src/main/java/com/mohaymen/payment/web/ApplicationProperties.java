package com.mohaymen.payment.web;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Validated
@ConfigurationProperties(prefix = "payment.web")
public record ApplicationProperties(@DefaultValue("10") @Min(1) int pageSize) {}
