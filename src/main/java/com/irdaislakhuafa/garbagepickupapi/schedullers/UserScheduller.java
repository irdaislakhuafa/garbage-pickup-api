package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserScheduller {
    private final MinIOFileService minIOFileService;
    private final UserRepository userRepository;

    @Value(value = "${minio.buckets.users}")
    private String BUCKET_USERS;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateImagePresignedUrl() throws Exception {
        log.info("updating presigned url for image users");

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
}