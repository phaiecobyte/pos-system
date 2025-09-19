package com.phaiecobyte.possystem.payload.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryReq {
    @NotBlank(message = "Category name in English is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
}
