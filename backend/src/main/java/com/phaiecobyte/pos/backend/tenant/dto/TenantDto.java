package com.phaiecobyte.pos.backend.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TenantDto {

    public record CreateReq(
            @NotBlank(message = "Tenant Identifier is required")
            String code,
            String businessTypeCode,
            @NotBlank(message = "Business Name is required")
            String businessName,
            String phone,
            String email,
            String address,
            String status,
            LocalDate subscriptionEndDate
    ) {}

    public record UpdateReq(
            String businessName,
            String contactPhone,
            Boolean isActive,
            LocalDate subscriptionEndDate
    ) {}

    public record Response(
            UUID id,
            String code,
            String businessName,
            String phone,
            String email,
            LocalDate subscriptionEndDate,
            String address,
            String status,
            String businessTypeCode,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {}
}