package com.mohaymen.store.clients.config;

import com.mohaymen.store.web.ApplicationProperties;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */
@Configuration
public class PaymentServiceClientConfig {
    @Bean
    RestClient paymentRestClient(ApplicationProperties properties) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(properties.paymentServiceUrl())
                .requestFactory(factory)
                .build();
    }

    @Bean
    public RestClient keycloakRestClient(ApplicationProperties properties) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(properties.keycloakAdminUrl())
                .requestFactory(factory)
                .build();
    }
}
