package com.phaiecobyte.pos.backend.media.model;

import lombok.*;

import java.util.UUID;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class MediaResponse {
    private UUID id;
    private String url;
}
