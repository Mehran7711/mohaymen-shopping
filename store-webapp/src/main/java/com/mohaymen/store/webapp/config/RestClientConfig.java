package com.mohaymen.store.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/28/26
 */
@Configuration
public class RestClientConfig {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Bean
    RestClient restClient() {

        return RestClient.builder().baseUrl(gatewayUrl).build();
    }
}
