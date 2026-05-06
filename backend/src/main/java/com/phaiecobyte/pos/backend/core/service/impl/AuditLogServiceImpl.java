package com.phaiecobyte.pos.backend.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phaiecobyte.pos.backend.core.model.AuditLog;
import com.phaiecobyte.pos.backend.core.repository.AuditLogRepository;
import com.phaiecobyte.pos.backend.core.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AtomicReference<ObjectMapper> objectMapper = new AtomicReference<ObjectMapper>(); // ប្រើសម្រាប់បម្លែង Object ទៅជា JSON String

    @Override
    public void logAction(String action, String moduleName, String entityName, UUID entityId, 
                          Object oldValue, Object newValue, String reason) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(action);
            auditLog.setModuleName(moduleName);
            auditLog.setEntityName(entityName);
            auditLog.setEntityId(entityId);
            
            // បម្លែង Object ទៅជា JSON (បើមាន)
            auditLog.setOldValue(oldValue != null ? objectMapper.get().writeValueAsString(oldValue) : null);
            auditLog.setNewValue(newValue != null ? objectMapper.get().writeValueAsString(newValue) : null);
            
            auditLog.setReason(reason);

            // ចំណាំ៖ userId និង ipAddress យើងដាក់បណ្ដោះអាសន្នសិន (Mock Data)
            // នៅពេលយើងធ្វើ Auth Module ចប់ យើងនឹងទាញយកពី SecurityContextHolder មកជំនួសវិញ
            auditLog.setUserId(UUID.fromString("00000000-0000-0000-0000-000000000000")); 
            auditLog.setIpAddress("127.0.0.1");

            auditLogRepository.save(auditLog);
            
        } catch (Exception e) {
            // យើងមិន throw Exception ទេ ព្រោះយើងមិនចង់ឱ្យគាំងប្រព័ន្ធធំដោយសារតែ error កត់ត្រា log
            log.error("បរាជ័យក្នុងការកត់ត្រា AuditLog សម្រាប់ {}: {}", entityName, e.getMessage());
        }
    }
}