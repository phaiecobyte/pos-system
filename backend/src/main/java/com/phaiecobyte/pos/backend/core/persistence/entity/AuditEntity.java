package com.phaiecobyte.pos.backend.core.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public abstract class AuditEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 55)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 55)
    private String updatedBy;
}
