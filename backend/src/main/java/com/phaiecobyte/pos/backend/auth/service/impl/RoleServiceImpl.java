package com.phaiecobyte.pos.backend.auth.service.impl;

import com.phaiecobyte.pos.backend.auth.dto.RoleDto;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.mapper.RoleMapper;
import com.phaiecobyte.pos.backend.auth.repository.RoleRepository;
import com.phaiecobyte.pos.backend.auth.service.RoleService;
import com.phaiecobyte.pos.backend.core.exception.AppException;
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

    @Override
    public List<RoleDto> list() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDto create(RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleDto update(UUID uuid, RoleDto dto) {
        Role role = roleRepository.findById(uuid)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Role is not found"));
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return roleMapper.toDto(role);
    }
}
