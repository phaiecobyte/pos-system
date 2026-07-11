package com.phaiecobyte.pos.backend.media.service.impl;//package com.phaiecobyte.mediaservice.service;
//
//import com.phaiecobyte.mediaservice.exception.NotFoundException;
//import com.phaiecobyte.mediaservice.model.MediaFile;
//import com.phaiecobyte.mediaservice.repository.MediaRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static com.phaiecobyte.constent.Constant.ALLOWED_FILE_TYPES;
//import static com.phaiecobyte.constent.Constant.MAX_FILE_SIZE;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MediaServiceImpl_backup implements MediaService {
//
//    private final MediaRepository repository;
//
//    @Value("${media.upload-dir}")
//    private String uploadDir;
//
//    @Override
//    public MediaFile upload(MultipartFile file) {
//        validateFile(file);
//
//        try {
//            //Date-based folders
//            LocalDateTime today = LocalDateTime.now();
//            String year = String.valueOf(today.getYear());
//            String month = String.valueOf(today.getMonth());
//            String day = String.valueOf(today.getDayOfMonth());
//
//            Path baseDir = Paths.get(uploadDir,year, month,day);
//            Files.createDirectories(baseDir);
//
//            //File info
//            String originalName = file.getOriginalFilename();
//            String extension = getExtension(originalName);
//
//            //Short readable name
//            String shortId = UUID.randomUUID().toString().substring(0,6);
//            String prefix = getFilePrefix(file.getContentType());
//
//            String storeName = prefix + "_" + shortId + "." + extension;
//
//            Path targetPath = baseDir.resolve(storeName);
//            Files.copy(file.getInputStream(),targetPath);
//
//            //
//            String url = String.format(
//                    "/api/v1/media/files/%s/%s/%s/%s",
//                    year,month,day,storeName
//            );
//
//
//            MediaFile media = MediaFile.builder()
//                    .originalName(originalName)
//                    .storeName(storeName)
//                    .contentType(file.getContentType())
//                    .size(file.getSize())
//                    .storagePath(targetPath.toString())
//                    .url(url)
//                    .build();
//
//            log.debug("File uploaded.......!");
//            return repository.save(media);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file", e);
//        }
//    }
//
//    @Override
//    public Resource loadAsResource(String year, String month, String date, String filename) {
//        try{
//            Path filePath = Paths.get(uploadDir,year,month,date,filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if(!resource.exists() || !resource.isReadable()){
//                throw new RuntimeException("File not found!" + filename);
//            }
//
//            log.debug("Resource is loaded...!");
//            return resource;
//
//        }catch (MalformedURLException e){
//            throw new RuntimeException("Could not read file:"+filename);
//        }
//    }
//
//    @Override
//    public void delete(UUID id) {
//        MediaFile mediaFile = repository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Media file not found!"));
//
//        try {
//            log.debug("Deleting media file {}",id);
//            Path filePath = Paths.get(mediaFile.getStoragePath());
//
//            // Delete physical file
//            if(Files.exists(filePath)){
//                log.debug("Deleted media file in physical!");
//                Files.delete(filePath);
//            }
//
//            // Delete DB record
//            log.debug("Deleted media file record in db");
//            repository.delete(mediaFile);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to delete media file",e);
//        }
//    }
//
//    //========================= Helper method ==========================
//    private String getExtension(String filename) {
//        if (filename == null || !filename.contains(".")) {
//            return "bin";
//        }
//        return filename.substring(filename.lastIndexOf('.') + 1);
//    }
//
//    private String getFilePrefix(String contentType) {
//        if (contentType == null) return "file";
//
//        if (contentType.startsWith("image/")) return "img";
//        if (contentType.startsWith("video/")) return "vid";
//        if (contentType.equals("application/pdf")) return "pdf";
//
//        return "file";
//    }
//
//    private void validateFile(MultipartFile file){
//        if(file.isEmpty()){
//            log.error("File is empty!");
//            throw new IllegalArgumentException("File is empty");
//        }
//        if(file.getSize() > MAX_FILE_SIZE){
//            log.error("File size exceed 5MB limit ");
//            throw new IllegalArgumentException("File size exceed 5MB limit");
//        }
//
//        String contentType = file.getContentType();
//        if(contentType == null || !ALLOWED_FILE_TYPES.contains(contentType)){
//            log.error("Invalid image type. Allowed: JPG, PNG, JPG, SVG, WEBP");
//            throw new IllegalArgumentException("Invalid image type. Allowed: JPG, PNG, JPG, SVG, WEBP");
//        }
//    }
//
//}
