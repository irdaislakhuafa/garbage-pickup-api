package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectsArgs;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserScheduller {
    private final MinIOFileService minIOFileService;
    private final UserRepository userRepository;
    private final MinioClient minioClient;

    @Value(value = "${minio.buckets.users}")
    private String BUCKET_USERS;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateImagePresignedUrl() throws Exception {
        log.info("updating presigned url for image contact us");

        // get list users with is deleted false
        var listUser = this.userRepository.findAllByIsDeleted(false);

        // override list users on image field with new presigned url
        listUser = listUser.stream().peek(user -> {
            try {
                if (user.getImage() != null) {
                    if (!user.getImage().isEmpty() && !user.getImage().isBlank()) {
                        final var fileName = this.minIOFileService.getFileNameFromPresignedUrl(user.getImage());
                        final var newImageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                                .builder()
                                .bucketName(this.BUCKET_USERS)
                                .fileName(fileName)
                                .build());
                        user.setImage(newImageLink);
                    }
                }

                user.setUpdatedAt(LocalDateTime.now());
                user.setUpdatedBy("scheduller");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }).toList();

        // save/update all users
        this.userRepository.saveAll(listUser);
    }

    //    @Scheduled(cron = "1 * * * * *")
    @Scheduled(cron = "0 0 0 1 * *")
    public void removeUnusedImage() {
        log.info("[{}] removing unused files", this.BUCKET_USERS);
        try {
            // find all object files from bucket
            final var listObjectFiles = this.minIOFileService.findAllObjects(ListObjectsArgs.builder()
                    .bucket(this.BUCKET_USERS)
                    .build());

            // find all data from table contact us
            final var listUsers = this.userRepository.findAllByIsDeleted(false);

            // filter all file names
            final var listUsersImageFiles = new ArrayList<String>();
            listUsers.forEach(contactUs -> {
                try {
                    if (contactUs.getImage() != null) {
                        if (!contactUs.getImage().isEmpty() && !contactUs.getImage().isBlank()) {
                            final var imageFileName = this.minIOFileService.getFileNameFromPresignedUrl(contactUs.getImage());
                            listUsersImageFiles.add(imageFileName);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });

            // check files, and remove it from bucket if file name doesn't exist in database
            final var listToBeDeleted = new ArrayList<DeleteObject>();
            for (final var item : listObjectFiles) {
                final var fileName = item.get().objectName();
                if (!listUsersImageFiles.contains(fileName)) {
                    listToBeDeleted.add(new DeleteObject(fileName));
                }
            }

            // delete all unused files
            final var listDeletedObjects = this.minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(this.BUCKET_USERS)
                    .objects(listToBeDeleted)
                    .build());

            for (final var err : listDeletedObjects) {
                log.error("error deleting object {}/{} : {}", err.get().bucketName(), err.get().objectName(), err.get().message());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("[{}] done", this.BUCKET_USERS);
    }
}