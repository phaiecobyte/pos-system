package com.phaiecobyte.pos.backend.common.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonPropertyOrder({"timestamp", "status", "success", "message", "data"})
@Schema(name = "ApiResponse", description = "ទម្រង់ស្តង់ដារសម្រាប់ការឆ្លើយតប API ទាំងអស់")
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;


    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }


    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}