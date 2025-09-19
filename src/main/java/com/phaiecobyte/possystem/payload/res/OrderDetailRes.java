package com.phaiecobyte.possystem.payload.res;

public record OrderDetailRes(
        Long productId,
        String productName,
        int quantity,
        double price
) {}
