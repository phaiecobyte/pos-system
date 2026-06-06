package com.phaiecobyte.pos.backend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final HttpStatus status;

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
