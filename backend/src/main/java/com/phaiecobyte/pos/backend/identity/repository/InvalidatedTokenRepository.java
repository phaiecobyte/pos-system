package com.phaiecobyte.pos.backend.identity.repository;

import com.phaiecobyte.pos.backend.identity.model.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    @Modifying
    @Query("DELETE FROM InvalidatedToken it WHERE it.expiryTime < CURRENT_TIMESTAMP")
    long deleteExpiredTokens();
}