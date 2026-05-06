package com.phaiecobyte.pos.backend.auth.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_auth_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    @ToString.Exclude
    private String password; //encrypt password

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;


    @Column(name = "is_active")
    private boolean active = true;


    // បង្កើតតារាងកណ្តាល user_roles ដោយស្វ័យប្រវត្តិ
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_auth_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> roles;



    // ការ Mapping យក Roles និង Permissions បញ្ចូលគ្នាដើម្បីឱ្យ Spring Security ស្គាល់
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            // បន្ថែម Role ចូល (ត្រូវមានពាក្យ ROLE_ នៅខាងមុខ)
            String roleName = role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName();
            authorities.add(new SimpleGrantedAuthority(roleName));
            // បន្ថែម Permissions ទាំងអស់របស់ Role នោះចូល
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
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

    @Override
    public boolean isEnabled() {
        return active;
    }
}