package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.UserInfo;
import com.mohaymen.store.service.IKeycloakService;
import com.mohaymen.store.service.ISecurityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService implements ISecurityService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final IKeycloakService keycloakService;

    @Override
    public String getLoginUserName() {
        // return "user";
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        Jwt jwt = (Jwt) authentication.getPrincipal();
        /*
        var username = jwt.getClaimAsString("preferred_username");
        var email = jwt.getClaimAsString("email");
        var name = jwt.getClaimAsString("name");
        var token = jwt.getTokenValue();
        var authorities = authentication.getAuthorities();
        */
        return jwt.getClaimAsString("preferred_username");
    }

    @Override
    public String getLoginUserId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getSubject();
    }

    @Override
    public UserInfo getCurrentUser(String userId) {
        String key = "user:" + userId;
        UserInfo cached = (UserInfo) redisTemplate.opsForValue().get(key);

        if (cached != null) {
            log.info("User fetch from cache: {}", cached);
            return cached;
        }

        UserInfo user = keycloakService.getUser(userId);
        redisTemplate.opsForValue().set(key, user, Duration.ofHours(1));
        return user;
    }
}
