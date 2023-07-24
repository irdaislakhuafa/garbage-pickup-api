package com.irdaislakhuafa.garbagepickupapi.configurations;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfiguration {
    @Value(value = "${minio.url}")
    private String URL;
    @Value(value = "${minio.secret-key}")
    private String SECRET_KEY;
    @Value(value = "${minio.access-key}")
    private String ACCESS_KEY;

    @Bean
    public MinioClient minioClient() {
        final var client = MinioClient.builder()
                .endpoint(URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
        return client;
    }
}