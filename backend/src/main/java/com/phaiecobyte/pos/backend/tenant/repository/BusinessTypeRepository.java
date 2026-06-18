package com.phaiecobyte.pos.backend.tenant.repository;

import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, UUID> {
    Optional<BusinessType> findByCode(String code);
}
