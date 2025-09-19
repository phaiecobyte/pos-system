package com.phaiecobyte.possystem.payload.res;

public record UserSummaryRes(
        Long id,
        String username,
        String email
) {
}
