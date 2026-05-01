package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.AuthRequest;
import com.phaiecobyte.pos.backend.auth.dto.AuthResponse;
import com.phaiecobyte.pos.backend.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest req);
    AuthResponse register(RegisterRequest req);
}
