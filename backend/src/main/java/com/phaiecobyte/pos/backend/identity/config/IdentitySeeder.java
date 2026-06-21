package com.phaiecobyte.pos.backend.identity.config;

import com.phaiecobyte.pos.backend.identity.repository.RoleRepository;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IdentitySeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UUID tenantId = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342ac");

    @Override
    public void run(String... args) throws Exception {

    }
}