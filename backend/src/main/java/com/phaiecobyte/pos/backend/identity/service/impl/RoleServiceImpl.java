package com.phaiecobyte.pos.backend.identity.service.impl;

import com.phaiecobyte.pos.backend.identity.dto.RoleDto;
import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.mapper.RoleMapper;
import com.phaiecobyte.pos.backend.identity.repository.RoleRepository;
import com.phaiecobyte.pos.backend.identity.service.RoleService;
import com.phaiecobyte.pos.backend.core.common.exception.AppException;
import com.phaiecobyte.pos.backend.tenant.api.TenantLookup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final TenantLookup tenantLookup;

    @Override
    public List<RoleDto> list() {
        UUID tenantId = tenantLookup.getCurrentTenantId();

        return roleRepository.findRoleByTenantId(tenantId)
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDto create(RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        role.setTenantId(tenantLookup.getCurrentTenantId());
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleDto update(UUID uuid, RoleDto dto) {
        Role role = roleRepository.findRoleByIdAndTenantId(uuid,tenantLookup.getCurrentTenantId())
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Role is not found"));
        role.setCode(dto.getName());
        role.setDescription(dto.getDescription());
        return roleMapper.toDto(role);
    }
}
