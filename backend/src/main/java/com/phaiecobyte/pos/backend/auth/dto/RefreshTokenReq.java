package com.phaiecobyte.pos.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshTokenReq {
    @NotBlank
    private String refreshToken;
}
