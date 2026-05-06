package com.phaiecobyte.pos.backend.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "t_auth_role")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;

    // បង្កើតតារាងកណ្តាល role_permissions ដោយស្វ័យប្រវត្តិ
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_auth_role_perm",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    private Set<Permission> permissions;
}