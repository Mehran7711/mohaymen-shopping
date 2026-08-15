package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.ISecurityClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/28/26
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityClientService implements ISecurityClientService {

    private final RestClient restClient;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Value("${oauth2.registrationId}")
    private String registrationId;

    @Override
    public String accessToken(Authentication authentication) {
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(
                        registrationId,
                        authentication.getName());

        if (client == null) {
            throw new RuntimeException("OAuth2AuthorizedClient not found");
        }

        return client.getAccessToken().getTokenValue();
    }


    @Override
    public RestClient.RequestHeadersSpec<?> get(
            Authentication authentication,
            String uri) {
        return restClient.get()
                .uri(uri)
                .headers(h ->
                        h.setBearerAuth(accessToken(authentication)));
    }

    @Override
    public RestClient.RequestBodySpec post(Authentication authentication, String uri) {


        return restClient.post()
                .uri(uri)
                .headers(h ->
                        h.setBearerAuth(accessToken(authentication)));
    }

    @Override
    public RestClient.RequestBodySpec put(Authentication authentication, String uri) {

        return restClient.put()
                .uri(uri)
                .headers(h ->
                        h.setBearerAuth(accessToken(authentication)));
    }

    @Override
    public RestClient.RequestHeadersSpec<?> delete(Authentication authentication, String uri) {

        return restClient.delete()
                .uri(uri)
                .headers(h ->
                        h.setBearerAuth(accessToken(authentication)));
    }
}
