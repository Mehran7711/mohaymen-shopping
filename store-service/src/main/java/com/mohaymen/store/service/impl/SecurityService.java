package com.mohaymen.store.service.impl;

import com.mohaymen.store.service.ISecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
@Service
@Slf4j
@AllArgsConstructor
public class SecurityService implements ISecurityService {

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
}
