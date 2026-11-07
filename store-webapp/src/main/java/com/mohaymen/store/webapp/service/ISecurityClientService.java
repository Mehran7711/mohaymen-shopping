package com.mohaymen.store.webapp.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/28/26
 */
public interface ISecurityClientService {
    String accessToken(Authentication authentication);

    RestClient.RequestHeadersSpec<?> get(Authentication authentication, String uri);

    RestClient.RequestBodySpec post(Authentication authentication, String uri);

    RestClient.RequestBodySpec put(Authentication authentication, String uri);

    RestClient.RequestHeadersSpec<?> delete(Authentication authentication, String uri);
}
