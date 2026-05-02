package com.phaiecobyte.pos.backend.core.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductRes {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean stockable;
    private Integer currentStock;
    private boolean active;

    // ចំណុចសំខាន់៖ យើងបញ្ជូនទៅ Frontend តែ ID និង ឈ្មោះ Category បានហើយ
    private UUID categoryId;
    private String categoryName;
}
