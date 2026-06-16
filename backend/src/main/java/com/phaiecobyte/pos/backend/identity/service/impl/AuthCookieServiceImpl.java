package com.phaiecobyte.pos.backend.identity.service.impl;

import com.phaiecobyte.pos.backend.identity.service.AuthCookieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthCookieServiceImpl implements AuthCookieService {
    @Value("${app.jwt-refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // Must change to true when production
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(refreshTokenExpirationMs))
                .build();
    }

    @Override
    public ResponseCookie clearRefreshToken() {
        return ResponseCookie.from("refreshToken","")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }
}
