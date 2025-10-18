package com.api.auth.controller;

import com.api.auth.dto.ProcessRequest;
import com.api.auth.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessService processService;

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> process(@RequestBody ProcessRequest request,
                                                       Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(processService.processText(email, request.getText()));
    }
}
