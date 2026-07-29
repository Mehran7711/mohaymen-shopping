package com.mohaymen.store.webapp.service;

import org.springframework.security.core.Authentication;

public interface ICartService {
    String getCart(Authentication authentication);
}
