package com.phaiecobyte.possystem.payload.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderDetailReq (
        @NotNull(message = "itemId is required") Long itemId,
        @Positive(message = "qty must be > 0") int qty,
        @Positive(message = "price must be > 0") double price
){}
