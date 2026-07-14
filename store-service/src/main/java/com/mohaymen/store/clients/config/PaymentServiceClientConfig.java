package com.mohaymen.store.clients.config;

import com.mohaymen.store.web.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */

@Configuration
public class PaymentServiceClientConfig {
    @Bean
    RestClient restClient(ApplicationProperties properties) {

        return RestClient.builder()
                .baseUrl(properties.paymentServiceUrl())
                .build();
    }
}
