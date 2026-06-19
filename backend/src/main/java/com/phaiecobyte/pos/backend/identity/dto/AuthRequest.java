package com.phaiecobyte.pos.backend.identity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @NotBlank
    private String tenantCode;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
