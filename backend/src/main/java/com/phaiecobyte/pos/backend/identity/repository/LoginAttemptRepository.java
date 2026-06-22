package com.phaiecobyte.pos.backend.identity.repository;

import com.phaiecobyte.pos.backend.identity.model.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, UUID> {
    Optional<LoginAttempt> findByUsernameAndTenantId(String username, UUID tenantId);

    void deleteByUsernameAndTenantId(String username, UUID tenantId);
}
