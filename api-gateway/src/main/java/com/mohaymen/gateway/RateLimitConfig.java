package com.mohaymen.gateway;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
class RateLimitConfig {

    private static final Pattern SUBJECT = Pattern.compile("\"sub\"\\s*:\\s*\"([^\"]+)\"");

    @Bean
    KeyResolver userOrIpKeyResolver() {
        return exchange -> Mono.just(rateLimitKey(exchange));
    }

    private static String rateLimitKey(ServerWebExchange exchange) {
        String subject = subject(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        if (subject != null) {
            return "user:" + subject;
        }
        if (exchange.getRequest().getRemoteAddress() == null
                || exchange.getRequest().getRemoteAddress().getAddress() == null) {
            return "ip:unknown";
        }
        return "ip:" + exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
    }

    private static String subject(String authorization) {
        if (authorization == null || !authorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return null;
        }
        String[] parts = authorization.substring(7).trim().split("\\.");
        if (parts.length < 2) {
            return null;
        }
        try {
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            Matcher matcher = SUBJECT.matcher(payload);
            return matcher.find() ? matcher.group(1) : null;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
