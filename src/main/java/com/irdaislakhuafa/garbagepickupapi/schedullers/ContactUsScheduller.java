package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.ContactUsRepository;
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
public class ContactUsScheduller {
    private final MinIOFileService minIOFileService;
    private final ContactUsRepository contactUsRepository;
    private final MinioClient minioClient;

    @Value(value = "${minio.buckets.contact-us}")
    private String BUCKET_CONTACT_US;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateImagePresignedUrl() {
        // get list contact us where is deleted false
        var listContactUs = this.contactUsRepository.findAllByIsDeleted(false);

        // override list contact us with new presigned url of image
        listContactUs = listContactUs.stream().peek(contactUs -> {
            try {
                if (contactUs.getImage() != null) {
                    if (!contactUs.getImage().isEmpty() && !contactUs.getImage().isBlank()) {
                        final var fileName = this.minIOFileService.getFileNameFromPresignedUrl(contactUs.getImage());
                        final var newUmageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                                .builder()
                                .bucketName(this.BUCKET_CONTACT_US)
                                .fileName(fileName)
                                .build());

                        contactUs.setImage(newUmageLink);
                    }
                }

                contactUs.setCreatedAt(LocalDateTime.now());
                contactUs.setCreatedBy("scheduller");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }).toList();

        // save/update list contact us
        this.contactUsRepository.saveAll(listContactUs);
    }

    //    @Scheduled(cron = "1 * * * * *")
    @Scheduled(cron = "0 0 0 1 * *")
    public void removeUnusedImage() {
        log.info("[{}] removing unused files", this.BUCKET_CONTACT_US);
        try {
            // find all object files from bucket
            final var listObjectFiles = this.minIOFileService.findAllObjects(ListObjectsArgs.builder()
                    .bucket(this.BUCKET_CONTACT_US)
                    .build());

            // find all data from table contact us
            final var listContactUs = this.contactUsRepository.findAllByIsDeleted(false);

            // filter all file names
            final var listContactUsImageFiles = new ArrayList<String>();
            listContactUs.forEach(contactUs -> {
                try {
                    if (contactUs.getImage() != null) {
                        if (!contactUs.getImage().isEmpty() && !contactUs.getImage().isBlank()) {
                            final var imageFileName = this.minIOFileService.getFileNameFromPresignedUrl(contactUs.getImage());
                            listContactUsImageFiles.add(imageFileName);
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
                if (!listContactUsImageFiles.contains(fileName)) {
                    listToBeDeleted.add(new DeleteObject(fileName));
                }
            }

            // delete all unused files
            final var listDeletedObjects = this.minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(this.BUCKET_CONTACT_US)
                    .objects(listToBeDeleted)
                    .build());

            for (final var err : listDeletedObjects) {
                log.error("error deleting object {}/{} : {}", err.get().bucketName(), err.get().objectName(), err.get().message());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("[{}] done", this.BUCKET_CONTACT_US);
    }

}