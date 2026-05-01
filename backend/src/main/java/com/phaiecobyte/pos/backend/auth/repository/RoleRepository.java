package com.phaiecobyte.pos.backend.auth.repository;

import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String roleName);
}
