package com.phaiecobyte.pos.backend.identity.controller;

import com.phaiecobyte.pos.backend.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.common.exception.AppException;
import com.phaiecobyte.pos.backend.identity.dto.AuthRequest;
import com.phaiecobyte.pos.backend.identity.dto.AuthResponse;
import com.phaiecobyte.pos.backend.identity.dto.TokenPair;
import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.service.AuthCookieService;
import com.phaiecobyte.pos.backend.identity.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthCookieService authCookieService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request,
            HttpServletResponse response) {

        TokenPair tokens = authService.authenticate(request);

        ResponseCookie cookie =
                authCookieService.createRefreshTokenCookie(tokens.getRefreshToken());

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(tokens.getAccessToken())
                        .build()
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(
                    value = "refreshToken",
                    required = false
            ) String refreshToken,
            HttpServletResponse response) {

        if(refreshToken == null){
            throw  new AppException(HttpStatus.UNAUTHORIZED,"Refresh token not found");
        }

        TokenPair tokens =
                authService.refreshToken(refreshToken);
        ResponseCookie cookie =
                authCookieService.createRefreshTokenCookie(
                tokens.getRefreshToken()
        );

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(tokens.getAccessToken())
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @AuthenticationPrincipal User currentUser,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        if (currentUser == null) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Unauthorize!");
        }
        authService.logout(currentUser,request);

        ResponseCookie cookie =
                authCookieService.clearRefreshToken();
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        ApiResponse<String> res = ApiResponse.<String>builder()
                .message("Logout successfully!")
                .build();

        return ResponseEntity.ok(res);
    }
}
