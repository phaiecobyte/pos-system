package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleDto> list();
    RoleDto create(RoleDto dto);
    RoleDto update(UUID uuid, RoleDto dto);
}
