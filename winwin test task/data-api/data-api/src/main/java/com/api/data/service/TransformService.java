package com.api.data.service;

import com.api.data.dto.TransformRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransformService {

    public String transformText(TransformRequest transformRequest) {
        if (transformRequest == null) {
            return "Transform request is null";
        }
        return transformRequest.getText().toUpperCase();
    }

}
