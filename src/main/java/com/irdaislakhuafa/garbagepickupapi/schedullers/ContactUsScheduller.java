package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.ContactUsRepository;
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
public class ContactUsScheduller {
    private final MinIOFileService minIOFileService;
    private final ContactUsRepository contactUsRepository;

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
}