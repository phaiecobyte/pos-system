package com.phaiecobyte.pos.backend.identity.service;

import com.phaiecobyte.pos.backend.identity.dto.AuthRequest;
import com.phaiecobyte.pos.backend.identity.dto.AuthResponse;
import com.phaiecobyte.pos.backend.identity.dto.RefreshTokenReq;
import com.phaiecobyte.pos.backend.identity.dto.RegisterReq;
import com.phaiecobyte.pos.backend.identity.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest req);
    AuthResponse register(RegisterReq req);
    void logout(User user, HttpServletRequest request);

    AuthResponse refreshToken(RefreshTokenReq request);
}
