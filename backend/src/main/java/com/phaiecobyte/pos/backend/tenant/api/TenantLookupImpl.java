package com.phaiecobyte.pos.backend.tenant.api;

import com.phaiecobyte.pos.backend.core.tenancy.CurrentUser;
import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.mapper.TenantMapper;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TenantLookupImpl implements TenantLookup{
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final CurrentUser currentUser;

    public TenantLookupImpl(TenantRepository tenantRepository, TenantMapper tenantMapper, CurrentUser currentUser) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.currentUser = currentUser;
    }

    @Override
    public Optional<TenantDto.Response> findByCode(String code) {
        return tenantRepository.findByCode(code)
                .map(tenantMapper::toDto);
    }

    @Override
    public Optional<TenantDto.Response> findById(UUID id) {
        return tenantRepository.findById(id)
                .map(tenantMapper::toDto);
    }

    @Override
    public UUID getCurrentTenantId() {
        return currentUser.getTenantId();
    }
}
