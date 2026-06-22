package com.phaiecobyte.pos.backend.identity.mapper;

import com.phaiecobyte.pos.backend.identity.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.identity.dto.UserDto;
import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "roles", source = "roleId")
    User toEntity(CreateUserReq req);

//    // Role -> String
    default String map(Role role) {
        return role.getCode();
    }

    // UUID -> Role
    default Role map(UUID id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}