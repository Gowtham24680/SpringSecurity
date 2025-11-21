package com.example.springSecurity.springsecurity.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // Extract ID Token (CORRECT WAY)
        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
        String idToken = oidcUser.getIdToken().getTokenValue();

        // Extract user info
        String googleId = oidcUser.getSubject(); // same as "sub"
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String picture = oidcUser.getPicture();

        // Access token (optional)
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName()
        );
        String accessToken = client.getAccessToken().getTokenValue();

        // Debug output
        System.out.println("Google Access Token = " + accessToken);
        System.out.println("Google ID Token = " + idToken);
        System.out.println("Google User ID = " + googleId);
        System.out.println("Google Email = " + email);
        System.out.println("Google Name = " + name);

        // TODO: Generate your own JWT...

        response.sendRedirect("/home");
    }
}