package com.phaiecobyte.pos.backend.identity.service;

import org.springframework.http.ResponseCookie;

public interface AuthCookieService {
    ResponseCookie createRefreshTokenCookie(String refreshToken);
    ResponseCookie clearRefreshToken();
}
