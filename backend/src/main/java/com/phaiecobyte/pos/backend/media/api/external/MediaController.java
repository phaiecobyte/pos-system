package com.phaiecobyte.pos.backend.media.api.external;


import com.phaiecobyte.pos.backend.core.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.media.model.MediaFile;
import com.phaiecobyte.pos.backend.media.dto.MediaResponse;
import com.phaiecobyte.pos.backend.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;



@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final MediaService service;

    // ========================= LIST =========================
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MediaFile>>> list(
            @ParameterObject Pageable pageable
            ) {
        Page<MediaFile> mediaFiles =
                service.listMediaFiles("A",pageable);

        return ResponseEntity.ok(
                ApiResponse.success(mediaFiles,"Media list retrieved")
        );
    }

    // ========================= GET METADATA =========================
    @GetMapping("/{id}/metadata")
    public ResponseEntity<ApiResponse<MediaFile>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        service.getById(id),
                        "Media retrieved successfully"
                )
        );
    }

    // ========================= UPLOAD =========================
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<MediaResponse>> upload(
            @RequestParam("file") MultipartFile file
    ) {
        MediaFile media = service.upload(file);

        MediaResponse response = new MediaResponse(
                media.getId(),
                "/media/files/" + media.getId()
        );

        return ResponseEntity.ok(
                ApiResponse.success(response,"File uploaded successfully")
        );
    }

    // ========================= DOWNLOAD (BY ID) =========================
    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") UUID id) {

        MediaService.MediaResource media = service.loadAsResource(id);
        Resource resource = new InputStreamResource(media.inputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.contentType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + media.filename() + "\""
                )
                .body(resource);
    }

    // ========================= DELETE (SOFT) =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") UUID id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(null,"Media deleted successfully")
        );
    }
}



