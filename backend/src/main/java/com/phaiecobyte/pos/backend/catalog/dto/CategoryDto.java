package com.phaiecobyte.pos.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CategoryDto() {
    public record CreateReq(
            @NotBlank(message = "ឈ្មោះប្រភេទមិនអាចទទេបានទេ")
            String name,
            String description
    ) {}

    public record Res(
            UUID id,
            String name,
            String description,
            boolean active
    ) {}
}
