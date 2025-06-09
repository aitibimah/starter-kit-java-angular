package com.yourcompany.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDataResponse<T> {
    private T data;
    private HttpStatus httpStatus;
    private String status;

    public static <T> ApiDataResponse<T> ok(T data) {
        return new ApiDataResponse<>(data, HttpStatus.OK, "success");
    }

    public static <T> ApiDataResponse<T> created(T data) {
        return new ApiDataResponse<>(data, HttpStatus.CREATED, "success");
    }

    public static <T> ApiDataResponse<T> noContent() {
        return new ApiDataResponse<>(null, HttpStatus.NO_CONTENT, "success");
    }
}