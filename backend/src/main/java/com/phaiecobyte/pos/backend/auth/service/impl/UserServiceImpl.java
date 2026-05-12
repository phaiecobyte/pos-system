package com.phaiecobyte.pos.backend.auth.service.impl;

import com.phaiecobyte.pos.backend.auth.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.auth.dto.UserDto;
import com.phaiecobyte.pos.backend.auth.entity.Role;
import com.phaiecobyte.pos.backend.auth.entity.User;
import com.phaiecobyte.pos.backend.auth.mapper.UserMapper;
import com.phaiecobyte.pos.backend.auth.repository.RoleRepository;
import com.phaiecobyte.pos.backend.auth.repository.UserRepository;
import com.phaiecobyte.pos.backend.auth.service.UserService;
import com.phaiecobyte.pos.backend.core.logging.LogAudit;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    @LogAudit(action = "READ",moduleName = "AUTH", entityName = "t_auth_user", defaultReason = "")
    public Page<UserDto> list(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional
    @LogAudit(action = "CREATE",moduleName = "AUTH", entityName = "t_auth_user", defaultReason = "")
    public UserDto create(CreateUserReq req) {
        if(userRepository.findByUsername(req.getUsername()).isPresent()){
            throw new AppException(HttpStatus.BAD_REQUEST,"Username is already exist");
        }

        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setActive(true);

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(req.getRoleId()));
        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    @LogAudit(action = "UPDATE",moduleName = "AUTH", entityName = "t_auth_user", defaultReason = "")
    public UserDto toggleUserStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND, "User not found!"));

        user.setActive(!user.isActive());

        return userMapper.toDto(user);
    }
}
