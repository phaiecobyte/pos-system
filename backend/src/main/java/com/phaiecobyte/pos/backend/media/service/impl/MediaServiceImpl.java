package com.phaiecobyte.pos.backend.media.service.impl;

import com.phaiecobyte.pos.backend.core.common.exception.AppException;
import com.phaiecobyte.pos.backend.media.model.MediaFile;
import com.phaiecobyte.pos.backend.media.repository.MediaRepository;
import com.phaiecobyte.pos.backend.media.service.MediaService;
import com.phaiecobyte.pos.backend.media.service.MediaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository repository;
    private final MediaStorage storage;

    @Value("${app.allow.file.type:''}")
    private String ALLOWED_FILE_TYPES;

//    @Value("${app.max.file.size}:0")
//    private long MAX_FILE_SIZE;

    // ========================= LIST =========================
    @Override
    public Page<MediaFile> listMediaFiles(String status, Pageable pageable) {
        log.debug("Listing media files: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        return repository.findAllByStatus(status,pageable);
    }

    // ========================= GET BY ID =========================
    @Override
    public MediaFile getById(UUID id) {
        return repository.findMediaFileByIdAndStatus(id,"A")
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND,"Media not found: " + id));
    }

    // ========================= UPLOAD =========================
    @Override
    public MediaFile upload(MultipartFile file) {

        log.info("Uploading file: name={}, size={}, type={}",
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType());

        validateFile(file);

        try {
            // 🔒 Read ONCE (important)
            byte[] bytes = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);

            LocalDateTime now = LocalDateTime.now();
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());

            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);
            String prefix = getFilePrefix(file.getContentType());

            String shortId = UUID.randomUUID().toString().substring(0, 6);
            String storedName = prefix + "_" + shortId + "." + extension;

            String storagePath = year + "/" + month + "/" + day + "/" + storedName;

            storage.save(inputStream, storagePath, file.getContentType());

            MediaFile media = MediaFile.builder()
                    .originalName(originalName)
                    .storedName(storedName)
                    .contentType(file.getContentType())
                    .size(bytes.length)
                    .extension(extension)
                    .storagePath(storagePath)
                    .build();

            log.info("File uploaded successfully: {}", storagePath);
            return repository.save(media);

        } catch (Exception e) {
            log.error("Media upload failed for file={}", file.getOriginalFilename(), e);
            throw new RuntimeException("Media upload failed", e);
        }
    }

    // ========================= LOAD RESOURCE (BY ID) =========================
    @Override
    public MediaResource loadAsResource(UUID id) {

        MediaFile media = getById(id);

        InputStream stream = storage.load(media.getStoragePath());

        log.debug("Loaded media resource: {}", media.getStoragePath());

        return new MediaResource(
                stream,
                media.getContentType(),
                media.getOriginalName()
        );
    }

    // ========================= DELETE (SOFT DELETE) =========================
    @Override
    public void delete(UUID id) {
        MediaFile media = getById(id);
        log.debug("Deleting media file {}",id);
        storage.delete(media.getStoragePath());
        log.debug("Media file: {} is deleted...!",id);
        repository.delete(media);
    }

    public void disable(UUID id){
        MediaFile media = getById(id);

        log.debug("disable media {}", id);

        media.setStatus("I");
        repository.save(media);
    }

    // ========================= HELPERS =========================
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "bin";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    private String getFilePrefix(String contentType) {
        if (contentType == null) return "file";

        if (contentType.startsWith("image/")) return "img";
        if (contentType.startsWith("video/")) return "vid";
        if (contentType.equals("application/pdf")) return "pdf";

        return "file";
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > 1024) {
            throw new IllegalArgumentException("File size exceeds limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_FILE_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type");
        }
    }
}
