package com.irdaislakhuafa.garbagepickupapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file) throws IOException;

    String upload(MultipartFile file, String directory) throws IOException;

    String upload(MultipartFile file, String directory, String fileName) throws IOException;

    String getExtension(String fileName);
}