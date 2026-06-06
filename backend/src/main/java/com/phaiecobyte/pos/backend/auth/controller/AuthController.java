package com.phaiecobyte.pos.backend.auth.controller;

import com.phaiecobyte.pos.backend.auth.dto.AuthRequest;
import com.phaiecobyte.pos.backend.auth.dto.AuthResponse;
import com.phaiecobyte.pos.backend.auth.dto.RefreshTokenReq;
import com.phaiecobyte.pos.backend.auth.dto.RegisterReq;
import com.phaiecobyte.pos.backend.auth.entity.User;
import com.phaiecobyte.pos.backend.auth.service.AuthService;
import com.phaiecobyte.pos.backend.auth.service.impl.AuthServiceImpl;
import com.phaiecobyte.pos.backend.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.common.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterReq req){
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenReq request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@AuthenticationPrincipal User currentUser, HttpServletRequest request) {
        if (currentUser == null) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Unauthorize!");
        }

        authService.logout(currentUser,request);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("Logout successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
