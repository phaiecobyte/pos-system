package com.phaiecobyte.pos.backend.tenant.api;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;

import java.util.Optional;

public interface TenantLookup {
    Optional<TenantDto.Response> findByCode(String code);
}
