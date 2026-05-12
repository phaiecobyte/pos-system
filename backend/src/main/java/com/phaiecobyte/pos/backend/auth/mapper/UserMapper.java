package com.phaiecobyte.pos.backend.auth.mapper;

import com.phaiecobyte.pos.backend.auth.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.auth.dto.UserDto;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "roles", source = "roleId")
    User toEntity(CreateUserReq req);

    // Role -> String
    default String map(Role role) {
        return role.getName();
    }

    // UUID -> Role
    default Role map(UUID id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}