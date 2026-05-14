package com.phaiecobyte.pos.backend.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

// Class មេសម្រាប់ក្តោប DTOs ទាំងអស់របស់ Tenant
public class TenantDto {

    // 1. DTO សម្រាប់ Request បង្កើតថ្មី
    public record CreateReq(
            @NotBlank(message = "Tenant Identifier is required")
            String tenantIdentifier,
            
            @NotBlank(message = "Business Name is required")
            String businessName,
            
            String contactPhone,
            LocalDate subscriptionEndDate
    ) {}

    // 2. DTO សម្រាប់ Request កែប្រែ (Update)
    public record UpdateReq(
            String businessName,
            String contactPhone
    ) {}

    // 3. DTO សម្រាប់ Response បញ្ចេញទៅ Client
    public record Response(
            UUID id,
            String tenantIdentifier,
            String businessName,
            String contactPhone,
            LocalDate subscriptionEndDate,
            boolean isActive
    ) {}
}