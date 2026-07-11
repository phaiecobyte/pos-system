package com.phaiecobyte.pos.backend.media.api.internal;

import com.phaiecobyte.pos.backend.media.service.MediaService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaFacade {

    MediaDto upload(MultipartFile file);

    MediaDto get(UUID id);

    MediaService.MediaResource load(UUID id);

    void delete(UUID id);
}