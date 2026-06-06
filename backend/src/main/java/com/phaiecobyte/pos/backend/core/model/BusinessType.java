package com.phaiecobyte.pos.backend.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_core_business_type")
@Getter @Setter
public class BusinessType {
    @Id
    @Column(name = "code", nullable = false, unique = true, updatable = false)
    private String code;
    @Column(length = 55)
    private String description;
}
