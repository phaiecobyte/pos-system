package com.phaiecobyte.pos.backend.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.phaiecobyte.pos.backend.core.model.AuditLog;
import com.phaiecobyte.pos.backend.core.repository.AuditLogRepository;
import com.phaiecobyte.pos.backend.core.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // បង្កើត ObjectMapper ដោយផ្ទាល់
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void logAction(String action, String moduleName, String entityName, UUID entityId,
                          Object oldValue, Object newValue, String reason) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(action);
            auditLog.setModuleName(moduleName);
            auditLog.setEntityName(entityName);
            auditLog.setEntityId(entityId);

            // លុប .get() ចេញ
            auditLog.setOldValue(oldValue != null ? objectMapper.writeValueAsString(oldValue) : null);
            auditLog.setNewValue(newValue != null ? objectMapper.writeValueAsString(newValue) : null);

            auditLog.setReason(reason);

            auditLog.setUserId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            auditLog.setIpAddress("127.0.0.1");

            auditLogRepository.save(auditLog);

        } catch (Exception e) {
            log.error("បរាជ័យក្នុងការកត់ត្រា AuditLog សម្រាប់ {}: {}", entityName, e.getMessage());
        }
    }
}