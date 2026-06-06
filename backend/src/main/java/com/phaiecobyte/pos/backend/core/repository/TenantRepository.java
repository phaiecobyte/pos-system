package com.phaiecobyte.pos.backend.tenant.repository;

import com.phaiecobyte.pos.backend.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findByTenantIdentifier(String tenantIdentifier);
    boolean existsByTenantIdentifier(String tenantIdentifier);
}