package com.phaiecobyte.pos.backend.media.api.internal;

import java.util.UUID;

public record MediaDto(
        UUID id,
        String originalName,
        String contentType,
        long size,
        String extension
) {
}