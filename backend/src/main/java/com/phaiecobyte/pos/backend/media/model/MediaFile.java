package com.phaiecobyte.pos.backend.media.model;

import com.phaiecobyte.pos.backend.core.persistence.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "t_media")
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
public class MediaFile extends TenantAwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String originalName;
    private String storedName;
    private String contentType;
    private long size;
    private String extension;
    private String storagePath;
    private String status;
}
