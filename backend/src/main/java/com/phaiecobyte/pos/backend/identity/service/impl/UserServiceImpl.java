package com.phaiecobyte.pos.backend.identity.service.impl;

import com.phaiecobyte.pos.backend.identity.dto.AssignRoleReq;
import com.phaiecobyte.pos.backend.identity.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.identity.dto.UserDto;
import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.mapper.UserMapper;
import com.phaiecobyte.pos.backend.identity.repository.RoleRepository;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import com.phaiecobyte.pos.backend.identity.service.UserService;
import com.phaiecobyte.pos.backend.core.common.logging.LogAudit;
import com.phaiecobyte.pos.backend.core.common.exception.AppException;
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

    @Override
    public UserDto assignRole(UUID id, AssignRoleReq req) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"User not found"));
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(req.getRoleIds()));

        if(roles.isEmpty()){
            throw new RuntimeException("Invalid roles or not exist in system");
        }

        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }
}
