package com.phaiecobyte.pos.backend.core.tenancy;

import java.util.UUID;

public interface CurrentUser {

    UUID getUserId();

    UUID getTenantId();

    String getUsername();
}