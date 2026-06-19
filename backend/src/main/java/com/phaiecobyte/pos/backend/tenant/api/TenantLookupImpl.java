package com.phaiecobyte.pos.backend.tenant.api;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.mapper.TenantMapper;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantLookupImpl implements TenantLookup{
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Override
    public Optional<TenantDto.Response> findByCode(String code) {
        return tenantRepository.findByCode(code)
                .map(tenantMapper::toDto);
    }
}
