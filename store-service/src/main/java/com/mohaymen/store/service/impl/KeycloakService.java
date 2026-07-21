package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.UserInfo;
import com.mohaymen.store.service.IKeycloakService;
import com.mohaymen.store.web.response.TokenResponse;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Author: Mehran
 * Created on: 7/20/26
 */
@Slf4j
@Service
public class KeycloakService implements IKeycloakService {

    private final RestClient restClient;
    private String accessToken;
    private Instant tokenExpiry;

    public KeycloakService(@Qualifier("keycloakRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public UserInfo getUser(String id) {

        log.info("getUser using keycloak admin api, userId: {}", id);
        String token = getAccessToken();

        return restClient
                .get()
                .uri("/admin/realms/store/users/{id}", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserInfo.class);
    }

    private String getAccessToken() {
        refreshToken(); // This will only refresh if needed
        return accessToken;
    }

    private void refreshToken() {

        if (accessToken != null && Instant.now().isBefore(tokenExpiry)) {
            return;
        }

        // Get new token from Keycloak
        TokenResponse tokenResponse = restClient
                .post()
                .uri("/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("client_id=admin-cli&username=admin&password=admin123&grant_type=password")
                .retrieve()
                .body(TokenResponse.class);

        assert tokenResponse != null;
        this.accessToken = tokenResponse.getAccessToken();
        this.tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpiresIn() - 60);
    }
}
