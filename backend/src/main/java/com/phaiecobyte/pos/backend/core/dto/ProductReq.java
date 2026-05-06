package com.phaiecobyte.pos.backend.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductReq {
    @NotBlank(message = "លេខកូដបាកូដមិនអាចទទេបានទេ")
    private String code;

    @NotBlank(message = "ឈ្មោះផលិតផលមិនអាចទទេបានទេ")
    private String name;

    private String description;

    @NotNull(message = "តម្លៃត្រូវតែកំណត់")
    private BigDecimal price;

    private boolean stockable;

    @NotNull(message = "ត្រូវតែជ្រើសរើសប្រភេទ (Category)")
    private UUID categoryId;

    private String imageUrl;
}
