package com.yourcompany.project.controller;

import com.yourcompany.project.domain.dto.ApiDataResponse;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    protected <T> ResponseEntity<ApiDataResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiDataResponse.ok(data));
    }

    protected <T> ResponseEntity<ApiDataResponse<T>> created(T data) {
        return ResponseEntity.status(201).body(ApiDataResponse.created(data));
    }

    protected <T> ResponseEntity<ApiDataResponse<T>> noContent() {
        return ResponseEntity.status(204).body(ApiDataResponse.noContent());
    }
}