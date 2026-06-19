package com.phaiecobyte.pos.backend.tenant.api;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;

import java.util.Optional;
import java.util.UUID;

public interface TenantLookup {
    Optional<TenantDto.Response> findByCode(String code);
    UUID getCurrentTenantId();
}
