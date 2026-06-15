package com.phaiecobyte.pos.backend.tenant.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_core_tenant")
@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "phone", length = 55)
    private String phone;

    @Column(name = "email", length = 55)
    private String email;

    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;

    @Column(name = "address", length = 55)
    private String address;

    @Column(name = "status", length = 10)
    private String status = Status.A.toString();

    @ManyToOne
    @JoinColumn(name = "business_type_code")
    private BusinessType businessType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 55)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 55)
    private String updatedBy;
}