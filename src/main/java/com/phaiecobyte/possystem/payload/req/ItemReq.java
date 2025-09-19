package com.phaiecobyte.possystem.payload.req;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ItemReq {
    @NotBlank(message = "Item name is required")
    private String name;

    @Positive(message = "categoryId must be positive")
    private long categoryId;

    @PositiveOrZero(message = "priceIn must be >= 0")
    private double priceIn;

    @Positive(message = "priceOut must be > 0")
    private double priceOut;

    @PositiveOrZero(message = "stock must be >= 0")
    private int stock;

    @Size(max = 1000, message = "description length must be <= 1000")
    private String description;
}
