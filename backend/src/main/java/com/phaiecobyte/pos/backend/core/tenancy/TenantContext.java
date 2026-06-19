package com.phaiecobyte.pos.backend.core.tenancy;

import java.util.UUID;

public final class TenantContext {

    private static final ThreadLocal<UUID> TENANT =
            new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenantId(UUID tenantId) {
        TENANT.set(tenantId);
    }

    public static UUID getTenantId() {
        return TENANT.get();
    }

    public static void clear() {
        TENANT.remove();
    }
}