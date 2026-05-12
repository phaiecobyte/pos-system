package com.phaiecobyte.pos.backend.auth.service;

import com.phaiecobyte.pos.backend.auth.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.auth.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Page<UserDto> list(Pageable pageable);
    UserDto create(CreateUserReq req);
    UserDto toggleUserStatus(UUID userId);
}
