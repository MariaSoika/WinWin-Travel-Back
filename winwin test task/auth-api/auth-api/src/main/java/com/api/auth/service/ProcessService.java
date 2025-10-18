package com.api.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProcessService {

    @Value("${data.api.url}")
    private String dataApiUrl;

    @Value("${internal.token}")
    private String internalToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> processText(String email, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Token", internalToken);

        Map<String, String> body = Map.of("text", text);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(dataApiUrl, entity, Map.class);

        String result = (String) response.getBody().get("result");

        return Map.of(
                "email", email,
                "original", text,
                "result", result
        );
    }
}
