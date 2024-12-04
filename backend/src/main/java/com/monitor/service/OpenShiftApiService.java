package com.monitor.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@Slf4j
public class OpenShiftApiService {
    @Getter
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final AuthService authService;

    public OpenShiftApiService(
            @Value("${openshift.url}") String openshiftUrl,
            AuthService authService) {
        this.baseUrl = openshiftUrl;
        this.authService = authService;
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders createHeaders() {
        if (!authService.hasCredentials()) {
            throw new IllegalStateException("Please login first");
        }

        String auth = authService.getUsername() + ":" + authService.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("Accept", "application/json");
        return headers;
    }

    public String getProjects() {
        return restTemplate.exchange(
            baseUrl + "/apis/project.openshift.io/v1/projects",
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            String.class
        ).getBody();
    }

    public String getDeployments(String namespace) {
        return restTemplate.exchange(
            baseUrl + "/apis/apps/v1/namespaces/{namespace}/deployments",
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            String.class,
            namespace
        ).getBody();
    }
} 