package com.phaiecobyte.pos.backend.media.repository;

import com.phaiecobyte.pos.backend.media.model.MediaFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<MediaFile, UUID> {
    Optional<MediaFile> findMediaFileByIdAndStatus(UUID id, String status);

    @Query("SELECT m FROM MediaFile m WHERE m.status='A' AND m.storagePath =:storagePath")
    Optional<MediaFile> findByStoragePath(@Param("storagePath") String storagePath);

    Page<MediaFile> findAllByStatus(String status, Pageable pageable);

}
