package com.phaiecobyte.pos.backend.identity.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String tenantCode;
    private final String username;
    private final String password;
    private final Object principal;

    // Login request
    public CustomAuthenticationToken(
            String tenantCode,
            String username,
            String password
    ) {
        super(Collections.emptyList());
        this.tenantCode = tenantCode;
        this.username = username;
        this.password = password;
        this.principal = null;
        setAuthenticated(false);
    }

    // Authenticated user
    public CustomAuthenticationToken(
            SecurityUser principal,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        this.username = principal.getUsername();
        this.password = null;
        this.tenantCode = null;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}