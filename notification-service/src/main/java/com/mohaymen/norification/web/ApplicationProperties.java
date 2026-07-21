package com.mohaymen.norification.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Validated
@ConfigurationProperties(prefix = "notification")
public record ApplicationProperties(String supportEmail) {}
