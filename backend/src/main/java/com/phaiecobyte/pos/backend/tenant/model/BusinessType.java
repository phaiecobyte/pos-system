package com.phaiecobyte.pos.backend.tenant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_core_business_type")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BusinessType {
    @Id
    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;
    @Column(length = 55)
    private String description;
}
