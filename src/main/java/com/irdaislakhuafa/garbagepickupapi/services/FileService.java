package com.irdaislakhuafa.garbagepickupapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

public interface FileService {
    String upload(MultipartFile file) throws Exception;

    String upload(MultipartFile file, String directory) throws Exception;

    String upload(MultipartFile file, String directory, String fileName) throws Exception;

    default String getExtension(String filePath) {
        final var path = Paths.get(filePath);
        final var fileName = path.getFileName().toString();
        final var dotIndex = fileName.lastIndexOf(".");

        switch (dotIndex) {
            case -1, 0 -> {
                return "";
            }
            default -> {
                return fileName.substring(dotIndex);
            }
        }
    }
}