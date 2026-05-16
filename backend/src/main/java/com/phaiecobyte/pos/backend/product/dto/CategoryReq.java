package com.phaiecobyte.pos.backend.product.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryReq {
    @NotBlank(message = "ឈ្មោះប្រភេទមិនអាចទទេបានទេ")
    private String name;
    private String description;
}

