package com.phaiecobyte.pos.backend.tenant.service.impl;

import com.phaiecobyte.pos.backend.common.exception.AppException;
import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import com.phaiecobyte.pos.backend.tenant.model.Tenant;
import com.phaiecobyte.pos.backend.tenant.mapper.TenantMapper;
import com.phaiecobyte.pos.backend.tenant.repository.BusinessTypeRepository;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import com.phaiecobyte.pos.backend.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final BusinessTypeRepository businessTypeRepository;

    @Override
    public Page<TenantDto.Response> list(Pageable pageable) {
        return tenantRepository.findAll(pageable)
                .map(tenantMapper::toDto);
    }

    @Override
    public TenantDto.Response getById(UUID id){
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Not found"));
        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantDto.Response create(TenantDto.CreateReq req) {
        Tenant tenant = tenantMapper.toEntity(req);

        BusinessType businessType = businessTypeRepository.findByCode(req.businessTypeCode())
                .orElseThrow(()-> new AppException(HttpStatus.BAD_REQUEST, "Business type code is not found!"));
        tenant.setBusinessType(businessType);
        if (tenantRepository.existsByCode(req.code())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Tenant Identifier is already taken!");
        }
        // កំណត់ថ្ងៃផុតកំណត់លំនាំដើម (ឧទាហរណ៍៖ ឱ្យសាកល្បងប្រើ ១ ខែ)
        if (req.subscriptionEndDate() == null) {
           tenant.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantDto.Response update(UUID id, TenantDto.UpdateReq req) {
        Tenant existTenant = tenantRepository.findById(id)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Tenant not found!"));

        tenantMapper.updateEntity(existTenant,req);

        return tenantMapper.toDto(tenantRepository.save(existTenant));
    }

    @Override
    public TenantDto.Response toggleStatus(UUID id) {
        return null;
    }
}
