package com.phaiecobyte.pos.backend.identity.service;

import java.util.UUID;

public interface LoginAttemptService {
    void recordFailedAttempt(String username, UUID tenantId);

    void recordSuccessfulAttempt(String username, UUID tenantId);

    boolean isAccountLocked(String username, UUID tenantId);

    void resetAttempts(String username, UUID tenantId);
}
