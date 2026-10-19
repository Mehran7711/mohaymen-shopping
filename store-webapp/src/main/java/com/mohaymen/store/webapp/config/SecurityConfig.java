package com.mohaymen.store.webapp.config;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@EnableWebSecurity
class SecurityConfig {
    private final String browserUrl;

    SecurityConfig(@Value("${oauth2.browser_url}") String browserUrl) {
        this.browserUrl = browserUrl;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c.requestMatchers(
                                "/js/*",
                                "/css/*",
                                "/images/*",
                                "/error",
                                "/webjars/**",
                                "/",
                                "/actuator/**",
                                "/api/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                request -> request.getRequestURI().startsWith("/api/")
                        )
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()));
        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        return (request, response, authentication) -> {
            String baseUrl = UriComponentsBuilder.fromUriString(request.getRequestURL().toString())
                    .replacePath(request.getContextPath())
                    .replaceQuery(null)
                    .build()
                    .toUriString();
            UriComponentsBuilder logout = UriComponentsBuilder.fromUriString(
                            browserUrl + "/realms/store/protocol/openid-connect/logout")
                    .queryParam("client_id", "store_client")
                    .queryParam("post_logout_redirect_uri", baseUrl);
            if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
                logout.queryParam("id_token_hint", oidcUser.getIdToken().getTokenValue());
            }
            response.sendRedirect(logout.encode(StandardCharsets.UTF_8).build().toUriString());
        };
    }
}
