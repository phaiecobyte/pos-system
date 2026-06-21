package com.phaiecobyte.pos.backend.core.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter @Setter
public abstract class TenantAwareEntity extends AuditEntity{
    @Column(name = "tenant_id", updatable = false)
    private UUID tenantId;
}
