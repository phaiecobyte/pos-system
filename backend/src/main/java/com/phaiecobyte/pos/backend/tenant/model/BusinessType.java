package com.phaiecobyte.pos.backend.tenant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_core_business_type")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BusinessType {
    @Id
    @Column(name = "id", length = 55)
    private String id;
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
