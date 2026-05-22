package com.training.backend_api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret:}")
    private String clientSecret;

    public Map login(
            String username,
            String password
    ) {

        String url =
                serverUrl
                + "/realms/"
                + realm
                + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("client_id", clientId);
        if (clientSecret != null && !clientSecret.isEmpty()) {
            body.add("client_secret", clientSecret);
        }
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        RestTemplate restTemplate =
                new RestTemplate();

        try {
            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            Map.class
                    );

            return response.getBody();
        } catch (org.springframework.web.client.RestClientResponseException e) {
            Map<String, Object> errorMap = new java.util.HashMap<>();
            errorMap.put("error", "Login failed");
            errorMap.put("details", e.getResponseBodyAsString());
            return errorMap;
        } catch (Exception e) {
            Map<String, Object> errorMap = new java.util.HashMap<>();
            errorMap.put("error", "Login failed");
            errorMap.put("details", e.getMessage());
            return errorMap;
        }
    }
}