package com.phaiecobyte.pos.backend.core.tenancy;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentTenant {
    public UUID getTenantId() {
        return TenantContext.getTenantId();
    }
}
