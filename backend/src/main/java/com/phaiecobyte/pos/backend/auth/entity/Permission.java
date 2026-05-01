package com.phaiecobyte.pos.backend.auth.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_auth_perm")
@Getter
@Setter
public class Permission extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;

}
