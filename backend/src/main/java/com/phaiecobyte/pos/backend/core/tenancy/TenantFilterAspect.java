package com.phaiecobyte.pos.backend.core.tenancy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    // ស្ទាក់ (Intercept) គ្រប់ Method ទាំងអស់នៅក្នុង Package repository របស់អ្នក
    @Before("execution(* com.phaiecobyte.pos.backend..*Repository+.*(..))")
    public void enableTenantFilter() {
        // ទាញយក tenantId ពីក្នុង Context ដែល Interceptor បានរក្សាទុក
        String tenantId = TenantContext.getCurrentTenant();

        if (tenantId != null && !tenantId.isEmpty()) {
            // បម្លែង (Unwrap) EntityManager ទៅជា Hibernate Session ដើម
            Session session = entityManager.unwrap(Session.class);
            
            // បើកដំណើរការ Filter និងបញ្ចូន Parameter
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        }
    }
}