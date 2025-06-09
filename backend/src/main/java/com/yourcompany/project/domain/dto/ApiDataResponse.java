package com.yourcompany.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDataResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiDataResponse<T> success(T data) {
        return new ApiDataResponse<>(true, "Success", data);
    }

    public static <T> ApiDataResponse<T> success(String message, T data) {
        return new ApiDataResponse<>(true, message, data);
    }

    public static <T> ApiDataResponse<T> error(String message) {
        return new ApiDataResponse<>(false, message, null);
    }
}