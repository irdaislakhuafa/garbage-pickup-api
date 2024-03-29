package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinIOFileServiceImpl implements MinIOFileService {
    private final MinioClient minioClient;

    @Value(value = "${minio.buckets.defaults}")
    private String BUCKET_DEFAULTS;

    @Value(value = "${minio.presigned-url.expired-in.seconds}")
    private int PRESIGNED_URL_EXPIRED_IN_SECONDS;

    @Override
    public String upload(Upload upload) throws Exception {
        if (upload.getBucketName() != null) {
            if (!upload.getBucketName().isEmpty() && !upload.getBucketName().isBlank()) {
                final var isBucketAlreadyExists = this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(upload.getBucketName()).build());
                if (!isBucketAlreadyExists) {
                    final var bucket = MakeBucketArgs.builder()
                            .bucket(upload.getBucketName())
                            .build();
                    this.minioClient.makeBucket(bucket);
                }
            }
        } else {
            final var bucket = MakeBucketArgs.builder()
                    .bucket(this.BUCKET_DEFAULTS)
                    .build();
            this.minioClient.makeBucket(bucket);
            upload.setBucketName(this.BUCKET_DEFAULTS);
        }

        // check file size
        if (upload.getSize() <= 0) {
            throw new BadRequestException(String.format("your size is %d, make sure your file is valid or not corrupt", upload.getSize()));
        }

        // check file name extension
        if (this.getExtension(upload.getFileName()).equals("")) {
            throw new BadRequestException("your file doesn't have any extension, please use file with valid extension");
        }

        final Supplier<String> getFileName = () -> {
            if (upload.getFileName() != null) {
                if (!upload.getFileName().isEmpty() && !upload.getFileName().isBlank()) {
                    return upload.getFileName();
                }
            }
            return UUID.randomUUID() + getExtension(upload.getFileName());
        };
        upload.setFileName(getFileName.get());

        minioClient.putObject(PutObjectArgs.builder()
                .contentType(upload.getContentType())
                .bucket(upload.getBucketName())
                .object(upload.getFileName())
                .stream(upload.getFileStream(), upload.getSize(), -1)
                .build());


        return upload.getFileName();
    }

    @Override
    public String getPresignedUrl(PresignedUrl presignedUrl) throws Exception {
        final var url = this.minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(presignedUrl.getBucketName())
                .object(presignedUrl.getFileName())
                .method(Method.GET)
                .expiry(this.PRESIGNED_URL_EXPIRED_IN_SECONDS)
                .build());
        return url;
    }

    @Override
    public String getFileNameFromPresignedUrl(String url) throws Exception {
        final var uri = new URI(url);
        final var fileName = Paths.get(uri.getPath()).getFileName().toString();
        return fileName;
    }

    @Override
    public Iterable<Result<Item>> findAllObjects(ListObjectsArgs args) {
        final var results = this.minioClient.listObjects(args);
        return results;
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        return this.upload(file, this.BUCKET_DEFAULTS);
    }

    @Override
    public String upload(MultipartFile file, String directory) throws Exception {
        return this.upload(file, directory, UUID.randomUUID() + this.getExtension(file.getOriginalFilename()));
    }

    @Override
    public String upload(MultipartFile file, String directory, String fileName) throws Exception {
        final var args = Upload.builder()
                .bucketName(directory)
                .contentType(file.getContentType())
                .fileName(fileName)
                .fileStream(file.getInputStream())
                .size(file.getSize())
                .build();
        return this.upload(args);
    }
}