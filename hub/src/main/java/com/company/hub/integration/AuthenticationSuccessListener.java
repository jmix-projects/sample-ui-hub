package com.company.hub.integration;

import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * On each successful Keycloak login the class extracts access token and stores it as session attribute to be used
 * by {@link MenuRetriever}.
 */
@Component
public class AuthenticationSuccessListener {

    private ObjectProvider<SessionData> sessionDataProvider;

    public AuthenticationSuccessListener(ObjectProvider<SessionData> sessionDataProvider) {
        this.sessionDataProvider = sessionDataProvider;
    }

    @EventListener
    public void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication instanceof OAuth2LoginAuthenticationToken) {
            OAuth2AccessToken accessToken = ((OAuth2LoginAuthenticationToken) authentication).getAccessToken();
            String tokenValue = accessToken.getTokenValue();
            sessionDataProvider.getObject().setAttribute("accessToken", tokenValue);
        }
    }
}
