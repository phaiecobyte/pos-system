package com.phaiecobyte.pos.backend.tenant.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "t_sys_tenant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends BaseEntity {

    @Column(name = "tenant_identifier", nullable = false, unique = true, updatable = false)
    private String tenantIdentifier; // ឧទាហរណ៍៖ "shop_001" (សម្រាប់យកទៅប្រើក្នុង Header X-TenantID)

    @Column(name = "business_name", nullable = false)
    private String businessName; // ឈ្មោះហាង ឬក្រុមហ៊ុន

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate; // ថ្ងៃផុតកំណត់ការប្រើប្រាស់ប្រព័ន្ធ

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // បិទ/បើក ហាង (ឧ. ពេលអត់បង់លុយ)
}