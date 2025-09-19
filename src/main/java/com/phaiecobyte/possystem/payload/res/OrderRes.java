package com.phaiecobyte.possystem.payload.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRes(
        Long id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status,
        UserSummaryRes user,
        List<OrderDetailRes> orderDetails
) {}