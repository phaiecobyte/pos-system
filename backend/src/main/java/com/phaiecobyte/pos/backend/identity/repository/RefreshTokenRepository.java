package com.phaiecobyte.pos.backend.identity.repository;

import com.phaiecobyte.pos.backend.identity.model.RefreshToken;
import com.phaiecobyte.pos.backend.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    void deleteByUser(User user); // សម្រាប់លុបពេល User Logout
}
