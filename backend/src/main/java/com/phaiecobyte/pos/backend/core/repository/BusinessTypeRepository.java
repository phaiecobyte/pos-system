package com.phaiecobyte.pos.backend.core.repository;

import com.phaiecobyte.pos.backend.core.model.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, UUID> {
    BusinessType findByCode(String code);
}
