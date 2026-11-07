package com.mohaymen.store.webapp.service;

import com.mohaymen.store.webapp.web.response.UserResponse;
import org.springframework.security.core.Authentication;

public interface IUserService {
    UserResponse getCurrentUser(Authentication authentication);
}
