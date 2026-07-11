package com.phaiecobyte.pos.backend.media.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url:http://localhost:9000}")
    private String url;

    @Value("${minio.access-key:phaiecobyte}")
    private String accessKey;

    @Value("${minio.secret-key:phaiecobyte@123}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}