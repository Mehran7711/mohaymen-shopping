package com.mohaymen.store.webapp.service.impl;

import com.mohaymen.store.webapp.service.IUserService;
import com.mohaymen.store.webapp.web.response.UserResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Override
    public UserResponse getCurrentUser(Authentication authentication) {

        if (!(authentication instanceof OAuth2AuthenticationToken oauth2Token)) {
            throw new IllegalStateException("Authentication is not OAuth2AuthenticationToken: "
                    + authentication.getClass().getName());
        }

        OAuth2User oauth2User = oauth2Token.getPrincipal();

        String id = oauth2User.getAttribute("sub");

        String username = oauth2User.getAttribute("preferred_username");

        String email = oauth2User.getAttribute("email");

        String firstName = oauth2User.getAttribute("given_name");

        String lastName = oauth2User.getAttribute("family_name");

        String fullName = oauth2User.getAttribute("name");

        String phone = oauth2User.getAttribute("my_phone");

        Boolean emailVerified = oauth2User.getAttribute("email_verified");

        List<String> roles = extractRoles(oauth2User);

        return new UserResponse(
                id, username, email, firstName, lastName, fullName, phone, Boolean.TRUE.equals(emailVerified), roles);
    }

    private List<String> extractRoles(OAuth2User user) {

        Map<String, Object> realmAccess = user.getAttribute("realm_access");

        if (realmAccess == null) {
            return Collections.emptyList();
        }

        Object roles = realmAccess.get("roles");

        if (!(roles instanceof List<?> roleList)) {
            return Collections.emptyList();
        }

        return roleList.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
