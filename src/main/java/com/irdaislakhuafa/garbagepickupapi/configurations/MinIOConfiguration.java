package com.irdaislakhuafa.garbagepickupapi.configurations;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
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
                .endpoint(this.URL)
                .credentials(this.ACCESS_KEY, this.SECRET_KEY)
                .build();
        return client;
    }
}