package com.phaiecobyte.possystem.payload.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReq {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Full name is required")
    private String fullName;
    private String role;
}