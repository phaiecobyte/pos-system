package com.phaiecobyte.pos.backend.core.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.phaiecobyte.pos.backend.core.annotation.LogAudit;
import com.phaiecobyte.pos.backend.core.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLogAspect {

    private final AuditLogService auditLogService;
    // ប្រើ ObjectMapper ដើម្បីបម្លែង Object ទៅជា JSON String
//    private final ObjectMapper objectMapper;
    // ២. បង្កើតវាដោយផ្ទាល់ជា private variable
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // ផ្លាស់ប្តូរមកប្រើ @Around ជំនួស @AfterReturning
    @Around("@annotation(logAudit)")
    public Object logAround(ProceedingJoinPoint joinPoint, LogAudit logAudit) throws Throwable {

        // ១. អនុញ្ញាតឱ្យ Method គោល (ឧទាហរណ៍ createCategory) ដំណើរការ និងចាប់យកលទ្ធផល (result)
        Object result = joinPoint.proceed();

        try {
            // ២. ទាញយក Entity ID តាមវិធីមួយដែលទុកចិត្តជាងមុន (ទាញចេញពី Field ផ្ទាល់)
            UUID entityId = extractIdSafely(result);

            // ៣. បម្លែងលទ្ធផលទៅជា JSON ដើម្បីកត់ត្រាជា New Value
            String newValueJson = result != null ? objectMapper.writeValueAsString(result) : null;

            if (entityId != null) {
                auditLogService.logAction(
                        logAudit.action(),
                        logAudit.moduleName(),
                        logAudit.entityName(),
                        entityId,
                        null,         // oldValue (ពិបាកចាប់យកដោយ AOP ដូច្នេះទុក null សិន)
                        newValueJson, // ដាក់ជា JSON String ជំនួសអោយ Object ផ្ទាល់
                        logAudit.defaultReason()
                );
                log.info("បានកត់ត្រា Audit ជោគជ័យសម្រាប់ {} (ID: {})", logAudit.moduleName(), entityId);
            } else {
                log.warn("មិនបានកត់ត្រា Audit ទេ ដោយសារមិនអាចទាញយក Entity ID ពី Object នៃ Class: {}",
                        (result != null ? result.getClass().getName() : "Null"));
            }

        } catch (Exception e) {
            log.error("មានកំហុសពេលព្យាយាមកត់ត្រា Audit: {}", e.getMessage());
        }

        // ៤. ត្រូវតែបញ្ចេញ result ត្រឡប់ទៅវិញ បើមិនអញ្ចឹងទេ API របស់អ្នកនឹងមិនមានទិន្នន័យត្រឡប់ទៅ Frontend ឡើយ
        return result;
    }

    // Method ជំនួយសម្រាប់ការទាញយក ID ឱ្យកាន់តែសុវត្ថិភាព ដោយមិនពឹងផ្អែកលើ Get Method របស់ Lombok
    private UUID extractIdSafely(Object target) {
        if (target == null) return null;
        try {
            // ព្យាយាមទាញយក Property 'id' ដោយផ្ទាល់ មិនបាច់ខ្វល់ថាវា Private ឬមាន Get Method ទេ
            Field idField = target.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object value = idField.get(target);
            if (value instanceof UUID) {
                return (UUID) value;
            }
        } catch (NoSuchFieldException e) {
            // ក្នុងករណី Class នោះអត់មាន field ឈ្មោះ 'id' ទេ
        } catch (Exception e) {
            log.error("កំហុសពេលទាញយក ID ពី Reflection: {}", e.getMessage());
        }
        return null;
    }
}