package com.phaiecobyte.pos.backend.identity.service;

import com.phaiecobyte.pos.backend.identity.dto.AssignRoleReq;
import com.phaiecobyte.pos.backend.identity.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.identity.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Page<UserDto> list(Pageable pageable);
    UserDto create(CreateUserReq req);
    UserDto toggleUserStatus(UUID userId);
    UserDto assignRole(UUID id, AssignRoleReq req);
}
