package com.phaiecobyte.pos.backend.identity.security;

import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import com.phaiecobyte.pos.backend.tenant.api.TenantLookup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider
        implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final TenantLookup tenantLookup;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) {

        CustomAuthenticationToken auth =
                (CustomAuthenticationToken) authentication;

        var tenant = tenantLookup
                .findByCode(auth.getTenantCode())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid tenant"));
        log.info("Tenant found: {} {}", tenant.id(), tenant.code());

        var userOpt = userRepository
                .findByUsernameAndTenantId(
                        auth.getUsername(),
                        tenant.id()
                );

        log.info(
                "User found: {}",
                userOpt.isPresent()
        );


        User user = userRepository
                .findByUsernameAndTenantId(
                        auth.getUsername(),
                        tenant.id()
                )
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid credentials"));



        log.info("User found: {}", user.getUsername());
        if (!passwordEncoder.matches(
                auth.getPassword(),
                user.getPassword()
        )) {
            throw new BadCredentialsException(
                    "Invalid credentials"
            );
        }


        SecurityUser principal =
                new SecurityUser(user);

        return new CustomAuthenticationToken(
                principal,
                principal.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}