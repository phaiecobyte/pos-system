package com.phaiecobyte.pos.backend.core.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRes {
    private UUID id;
    private String name;
    private String description;
    private boolean active;
}
