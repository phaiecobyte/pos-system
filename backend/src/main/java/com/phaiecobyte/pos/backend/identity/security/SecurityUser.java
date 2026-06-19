package com.phaiecobyte.pos.backend.identity.security;


import com.phaiecobyte.pos.backend.identity.model.Permission;
import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.model.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SecurityUser  implements UserDetails{
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for(Role role : user.getRoles()){
            String roleName = role.getName().startsWith("ROLE_")
                    ? role.getName()
                    : "ROLE_" + role.getName();
            authorities.add(new SimpleGrantedAuthority(roleName));

            for(Permission permission : role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getName())
                );
            }
        }

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public User getUser() {
        return user;
    }

}
