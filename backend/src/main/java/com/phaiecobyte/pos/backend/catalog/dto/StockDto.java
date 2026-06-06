package com.phaiecobyte.pos.backend.catalog.dto;

import com.phaiecobyte.pos.backend.catalog.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record StockDto() {
    public record Req(
            @NotNull(message = "សូមជ្រើសរើសទំនិញ")
            UUID productId,

            @NotNull(message = "សូមជ្រើសរើសប្រភេទប្រតិបត្តិការ (IN/OUT)")
            TransactionType type,

            @NotNull(message = "ចំនួនមិនអាចទទេបានទេ")
            @Min(value = 1, message = "ចំនួនត្រូវតែធំជាង ០")
            Integer quantity,

            String remark
    ) {}
}
