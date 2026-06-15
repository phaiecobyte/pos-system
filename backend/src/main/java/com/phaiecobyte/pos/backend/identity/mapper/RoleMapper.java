package com.phaiecobyte.pos.backend.identity.mapper;

import com.phaiecobyte.pos.backend.identity.dto.RoleDto;
import com.phaiecobyte.pos.backend.identity.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto (Role role);
    Role toEntity(RoleDto dto);
}
