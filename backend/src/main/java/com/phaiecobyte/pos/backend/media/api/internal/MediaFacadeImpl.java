package com.phaiecobyte.pos.backend.media.api.internal;

import com.phaiecobyte.pos.backend.media.model.MediaFile;
import com.phaiecobyte.pos.backend.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaFacadeImpl implements MediaFacade {

    private final MediaService mediaService;

    @Override
    public MediaDto upload(MultipartFile file) {

        MediaFile media = mediaService.upload(file);

        return toDto(media);
    }

    @Override
    public MediaDto get(UUID id) {
        return toDto(mediaService.getById(id));
    }

    @Override
    public MediaService.MediaResource load(UUID id) {
        return mediaService.loadAsResource(id);
    }

    @Override
    public void delete(UUID id) {
        mediaService.delete(id);
    }

    private MediaDto toDto(MediaFile media) {
        return new MediaDto(
                media.getId(),
                media.getOriginalName(),
                media.getContentType(),
                media.getSize(),
                media.getExtension()
        );
    }
}