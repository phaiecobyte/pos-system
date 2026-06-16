package com.phaiecobyte.pos.backend.identity.service;

import com.phaiecobyte.pos.backend.identity.dto.*;
import com.phaiecobyte.pos.backend.identity.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    TokenPair authenticate(AuthRequest req);
    void logout(User user, HttpServletRequest request);
    TokenPair refreshToken(String refreshToken);
}
