package com.phaiecobyte.pos.backend.auth.mapper;

import com.phaiecobyte.pos.backend.auth.dto.RoleDto;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto (Role role);
    Role toEntity(RoleDto dto);
}
