package com.api.data.controller;

import com.api.data.dto.TransformRequest;
import com.api.data.service.TransformService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransformController {

    private final TransformService transformService;

    @Value("${internal.token}")
    private String internalToken;

    @PostMapping("/transform")
    public ResponseEntity<Map<String, String>> transformText (
            @RequestHeader("X-Internal-Token") String token,
            @RequestBody TransformRequest transformRequest){
        if (!internalToken.equals(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "invalid internal token"));
        }
        String result = transformService.transformText(transformRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Result", result));
    }

}
