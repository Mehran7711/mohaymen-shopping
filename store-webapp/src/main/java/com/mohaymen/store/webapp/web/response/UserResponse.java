package com.mohaymen.store.webapp.web.response;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 8/8/26
 */
public record UserResponse(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        String fullName,
        String phone,
        boolean emailVerified,
        List<String> roles) {}
