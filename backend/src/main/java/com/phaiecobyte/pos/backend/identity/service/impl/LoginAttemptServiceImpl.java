package com.phaiecobyte.pos.backend.identity.service.impl;

import com.phaiecobyte.pos.backend.identity.model.LoginAttempt;
import com.phaiecobyte.pos.backend.identity.repository.LoginAttemptRepository;
import com.phaiecobyte.pos.backend.identity.service.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    @Value("${app.security.max-login-attempts:5}")
    private int maxLoginAttempts;

    @Value("${app.security.lockout-duration-minutes:15}")
    private int lockoutDurationMinutes;

    @Override
    @Transactional
    public void recordFailedAttempt(String username, UUID tenantId) {
        LoginAttempt loginAttempt = loginAttemptRepository
                .findByUsernameAndTenantId(username, tenantId)
                .orElse(LoginAttempt.builder()
                        .username(username)
                        .tenantId(tenantId)
                        .attemptCount(0)
                        .isLocked(false)
                        .build());

        loginAttempt.setAttemptCount(loginAttempt.getAttemptCount() + 1);
        loginAttempt.setLastAttemptTime(LocalDateTime.now());

        if (loginAttempt.getAttemptCount() >= maxLoginAttempts) {
            loginAttempt.setLocked(true);
            loginAttempt.setLockUntilTime(LocalDateTime.now().plusMinutes(lockoutDurationMinutes));
            log.warn("Account locked for user: {} in tenant: {} after {} failed attempts",
                    username, tenantId, loginAttempt.getAttemptCount());
        }

        loginAttemptRepository.save(loginAttempt);
    }

    @Override
    @Transactional
    public void recordSuccessfulAttempt(String username, UUID tenantId) {
        loginAttemptRepository.deleteByUsernameAndTenantId(username, tenantId);
    }

    @Override
    public boolean isAccountLocked(String username, UUID tenantId) {
        LoginAttempt loginAttempt = loginAttemptRepository
                .findByUsernameAndTenantId(username, tenantId)
                .orElse(null);

        if (loginAttempt == null || !loginAttempt.isLocked()) {
            return false;
        }

        // Check if lock period has expired
        if (loginAttempt.getLockUntilTime() != null 
                && LocalDateTime.now().isAfter(loginAttempt.getLockUntilTime())) {
            // Unlock the account
            resetAttempts(username, tenantId);
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void resetAttempts(String username, UUID tenantId) {
        loginAttemptRepository.deleteByUsernameAndTenantId(username, tenantId);
    }
}
