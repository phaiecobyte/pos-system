package com.phaiecobyte.pos.backend.identity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_identity_permission")
@Getter @Setter
public class Permission{
    @Id
    @Column(length = 55)
    private String id;

    @Column(name = "description")
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();
}
