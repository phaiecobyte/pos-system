package com.phaiecobyte.pos.backend.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils(){}

    public static boolean hasRole(String role){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        assert auth != null;
        return auth.getAuthorities()
                .stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_"+role));
    }
}
