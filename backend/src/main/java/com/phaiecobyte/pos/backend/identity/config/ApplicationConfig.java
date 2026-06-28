package com.phaiecobyte.pos.backend.identity.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaiecobyte.pos.backend.core.tenancy.TenantContext;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import com.phaiecobyte.pos.backend.identity.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig{
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        UUID tenantId = TenantContext.getTenantId();
        if(tenantId == null){
            tenantId = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342ca");
        }
        UUID finalTenantId = tenantId;
        return username -> userRepository.findByUsernameAndTenantId(username, finalTenantId)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        return username -> userRepository.findByUsername(username)
//                .map(SecurityUser::new)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}