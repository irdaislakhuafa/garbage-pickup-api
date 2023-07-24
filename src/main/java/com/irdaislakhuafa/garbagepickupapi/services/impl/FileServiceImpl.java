package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value(value = "${app.config.upload-dir.parent}")
    private String UPLOAD_DIR;

    @Override
    public String upload(MultipartFile file) throws Exception {
        return this.upload(file, "");
    }

    @Override
    public String upload(MultipartFile file, String directory) throws Exception {
        return this.upload(file, directory, (UUID.randomUUID() + this.getExtension(file.getOriginalFilename())));
    }

    @Override
    public String upload(MultipartFile file, String directory, String fileName) throws Exception {
        final var dir = new File(this.UPLOAD_DIR + directory);
        if (!dir.exists()) {
            final var isCreated = dir.mkdirs();
            if (!isCreated) {
                throw new Exception(String.format("cannot create directory '%s'", dir.getPath()));
            }
        }

        if (file == null) {
            throw new Exception("file parameter cannot be null");
        }

        if (file.isEmpty()) {
            throw new Exception("file parameter cannot be empty");
        }

        final var bytes = file.getBytes();
        final var path = Paths.get(dir.getPath(), fileName);
        Files.write(path, bytes);

        return path.toString();
    }
}