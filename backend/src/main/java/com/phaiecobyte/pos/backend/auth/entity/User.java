package com.phaiecobyte.pos.backend.auth.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_auth_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; //encrypt password

    @Column(nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "is_active")
    private boolean active = true;
}
