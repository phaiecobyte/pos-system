package com.phaiecobyte.pos.backend.core.service;

import com.phaiecobyte.pos.backend.core.dto.TenantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TenantService {
    Page<TenantDto.Response> list(Pageable pageable);
    TenantDto.Response getById(UUID id);
    TenantDto.Response create(TenantDto.CreateReq req);
    TenantDto.Response update(UUID id, TenantDto.UpdateReq req);
    TenantDto.Response toggleStatus(UUID id);
}
