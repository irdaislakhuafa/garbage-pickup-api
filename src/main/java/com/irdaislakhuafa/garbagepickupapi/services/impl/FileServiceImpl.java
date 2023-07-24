package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Value(value = "${app.config.upload-dir}")
    private String UPLOAD_DIR;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if (file == null) {
            throw new IOException("file parameter cannot be null");
        }

        if (file.isEmpty()) {
            throw new IOException("file parameter cannot be empty");
        }
        final var bytes = file.getBytes();
        final var path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
        Files.write(path, bytes);

        return path.toString();
    }
}