package com.phaiecobyte.pos.backend.identity.scheduler;

import com.phaiecobyte.pos.backend.identity.repository.InvalidatedTokenRepository;
import com.phaiecobyte.pos.backend.identity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Cleanup expired tokens every hour (3600000 ms)
     * This prevents the database from growing indefinitely with expired tokens
     */
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("Starting token cleanup job");
        
        try {
            // Clean up invalidated tokens that have expired
            long invalidTokensDeleted = invalidatedTokenRepository.deleteExpiredTokens();
            log.info("Deleted {} expired invalidated tokens", invalidTokensDeleted);

            // Clean up refresh tokens that have expired
            long refreshTokensDeleted = refreshTokenRepository.deleteExpiredTokens();
            log.info("Deleted {} expired refresh tokens", refreshTokensDeleted);

            log.info("Token cleanup job completed successfully");
        } catch (Exception e) {
            log.error("Error during token cleanup", e);
        }
    }
}
