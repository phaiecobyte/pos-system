package com.phaiecobyte.pos.backend.tenant.model;


import com.phaiecobyte.pos.backend.core.persistence.entity.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_core_tenant")
@Getter @Setter
public class Tenant extends AuditEntity {
    @Id
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", length = 35)
    private String phone;

    @Column(name = "email", length = 35)
    private String email;

    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "status", length = 10)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type_id")
    private BusinessType businessType;

    @PrePersist
    protected void onCreate(){
        if(this.id == null){
            this.id = UUID.randomUUID();
        }
    }
}