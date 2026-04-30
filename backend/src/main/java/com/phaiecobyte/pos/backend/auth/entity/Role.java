package com.phaiecobyte.pos.backend.auth.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "t_auth_roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String roleName;

    private String description;
}