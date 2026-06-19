package com.phaiecobyte.pos.backend.identity.repository;

import com.phaiecobyte.pos.backend.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String roleName);

    List<Role> findRoleByTenantId(UUID tenantId);

    Optional<Role> findRoleByIdAndTenantId(UUID tenantId, UUID id);

    Optional<Role> findByNameAndTenantId(
            String name,
            UUID tenantId
    );

    List<Role> findByIdInAndTenantId(
            Collection<UUID> ids,
            UUID tenantId
    );
}
