package com.phaiecobyte.pos.backend.core.dto;

import com.phaiecobyte.pos.backend.core.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class StockReq {
    @NotNull(message = "សូមជ្រើសរើសទំនិញ")
    private UUID productId;

    @NotNull(message = "សូមជ្រើសរើសប្រភេទប្រតិបត្តិការ (IN/OUT)")
    private TransactionType type;

    @NotNull(message = "ចំនួនមិនអាចទទេបានទេ")
    @Min(value = 1, message = "ចំនួនត្រូវតែធំជាង ០")
    private Integer quantity;

    private String remark;
}