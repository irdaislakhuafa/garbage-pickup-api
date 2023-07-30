package com.irdaislakhuafa.garbagepickupapi.services;

import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;

public interface MinIOFileService extends FileService {
    /**
     * @return String return value is file name of object/file, you can use getPresignetUrl to get file url
     */
    String upload(Upload upload) throws Exception;

    /**
     * @return String use this method to get presigned url
     */
    String getPresignedUrl(PresignedUrl presignedUrl) throws Exception;

    String getFileNameFromPresignedUrl(String url) throws Exception;

    Iterable<Result<Item>> findAllObjects(ListObjectsArgs args);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    class Upload {
        private String bucketName;
        private String fileName;
        private InputStream fileStream;
        private String contentType;
        private long size;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class PresignedUrl {
        private String bucketName;
        private String fileName;
    }
}