package com.phaiecobyte.pos.backend.core.service;

import java.util.UUID;

public interface AuditLogService {
    void logAction(String action, String moduleName, String entityName, UUID entityId, 
                   Object oldValue, Object newValue, String reason);
}