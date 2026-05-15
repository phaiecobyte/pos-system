package com.phaiecobyte.pos.backend.tenant.service;

import com.phaiecobyte.pos.backend.core.exception.AppException;
import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.entity.Tenant;
import com.phaiecobyte.pos.backend.tenant.mapper.TenantMapper;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService{
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

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
        // ត្រួតពិនិត្យក្រែងលោមានឈ្មោះ Identifier នេះរួចហើយ
        if (tenantRepository.existsByTenantIdentifier(req.tenantIdentifier())) {
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
