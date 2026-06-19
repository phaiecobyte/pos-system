package com.phaiecobyte.pos.backend.core.persistence.listener;

import com.phaiecobyte.pos.backend.core.persistence.entity.TenantAwareEntity;
import com.phaiecobyte.pos.backend.tenant.api.TenantLookup;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantEntityListener {

    private final TenantLookup tenantLookup;

    @PrePersist
    public void prePersist(Object entity) {

        if (entity instanceof TenantAwareEntity tenantEntity) {

            if (tenantEntity.getTenantId() == null) {

                tenantEntity.setTenantId(
                        tenantLookup.getCurrentTenantId()
                );
            }
        }
    }
}