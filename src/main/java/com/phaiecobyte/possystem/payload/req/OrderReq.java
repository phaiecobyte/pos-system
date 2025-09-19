package com.phaiecobyte.possystem.payload.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record OrderReq(
        BigDecimal totalAmount,
        @NotBlank(message = "status is required") String status,
        Long userId,
        @NotNull(message = "orderDetails is required") @Size(min = 1, message = "order must have at least 1 detail") @Valid List<OrderDetailReq> orderDetails
) {}
