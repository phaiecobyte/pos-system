package com.phaiecobyte.pos.backend.identity.security;

import com.phaiecobyte.pos.backend.core.tenancy.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserImpl implements CurrentUser {

    @Override
    public UUID getUserId() {

        SecurityUser principal = getPrincipal();

        return principal.getUserId();
    }

    @Override
    public UUID getTenantId() {

        SecurityUser principal = getPrincipal();

        return principal.getTenantId();
    }

    @Override
    public String getUsername() {

        SecurityUser principal = getPrincipal();

        return principal.getUsername();
    }

    private SecurityUser getPrincipal() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (SecurityUser) authentication.getPrincipal();
    }
}