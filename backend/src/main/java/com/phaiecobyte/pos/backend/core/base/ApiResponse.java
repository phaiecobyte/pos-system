package com.phaiecobyte.pos.backend.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonPropertyOrder({"timestamp", "status", "success", "message", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;       // រក្សាទុក HTTP Status Code (ឧ. 200, 400, 500)
    private boolean success;  // បន្ថែម Boolean ងាយស្រួលឆែកត្រង់ៗ
    private String message;
    private T data;

    // Factory Method សម្រាប់ការឆ្លើយតបជោគជ័យ (Success)
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // Factory Method សម្រាប់ការឆ្លើយតបពេលមានកំហុស (Error)
    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .success(false)
                .message(message)
                .data(null) // វានឹងត្រូវបានលាក់មិនឱ្យបង្ហាញក្នុង JSON ដោយសារ @JsonInclude
                .build();
    }
}