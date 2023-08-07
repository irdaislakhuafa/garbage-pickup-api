package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectsArgs;
import io.minio.messages.DeleteObject;
import jakarta.transaction.Transactional;
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
public class VoucherScheduller {
    private final MinIOFileService minIOFileService;
    private final VoucherRepository voucherRepository;
    private final MinioClient minioClient;

    @Value(value = "${minio.buckets.vouchers}")
    private String BUCKET_VOUCHERS;

    // https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
    @Scheduled(cron = "0 0 0 * * *") // at 00:00:00 each day

    public void updateImagePresignedUrl() throws Exception {
        log.info("updating presigned url for image vouchers");

        // get all voucher with is deleted false
        var listVoucher = this.voucherRepository.findAllByIsDeleted(false);

        // override value of listVouchers (peek is like map but not change return result
        // structure)
        listVoucher = listVoucher.stream().peek(voucher -> {
            try {
                if (voucher.getImage() != null) {
                    if (!voucher.getImage().isEmpty() && !voucher.getImage().isBlank()) {
                        // get file name by method from MinIOFileService
                        final var fileName = this.minIOFileService.getFileNameFromPresignedUrl(voucher.getImage());
                        final var newImageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                                .builder()
                                .bucketName(this.BUCKET_VOUCHERS)
                                .fileName(fileName)
                                .build());

                        // update signed url for image
                        voucher.setImage(newImageLink);
                    }
                }

                voucher.setUpdatedAt(LocalDateTime.now());
                voucher.setUpdatedBy("scheduller");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }).toList();

        // save all users
        this.voucherRepository.saveAll(listVoucher);
        log.info("success update presigned url for image vouchers");
    }

    // @Scheduled(cron = "1 * * * * *")
    @Scheduled(cron = "0 0 0 1 * *")
    public void removeUnusedImage() {
        log.info("[{}] removing unused files", this.BUCKET_VOUCHERS);
        try {
            // find all object files from bucket
            final var listObjectFiles = this.minIOFileService.findAllObjects(ListObjectsArgs.builder()
                    .bucket(this.BUCKET_VOUCHERS)
                    .build());

            // find all data from table contact us
            final var listVouchers = this.voucherRepository.findAllByIsDeleted(false);

            // filter all file names
            final var listVouchersImageFiles = new ArrayList<String>();
            listVouchers.forEach(contactUs -> {
                try {
                    if (contactUs.getImage() != null) {
                        if (!contactUs.getImage().isEmpty() && !contactUs.getImage().isBlank()) {
                            final var imageFileName = this.minIOFileService
                                    .getFileNameFromPresignedUrl(contactUs.getImage());
                            listVouchersImageFiles.add(imageFileName);
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
                if (!listVouchersImageFiles.contains(fileName)) {
                    listToBeDeleted.add(new DeleteObject(fileName));
                }
            }

            // delete all unused files
            final var listDeletedObjects = this.minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(this.BUCKET_VOUCHERS)
                    .objects(listToBeDeleted)
                    .build());

            for (final var err : listDeletedObjects) {
                log.error("error deleting object {}/{} : {}", err.get().bucketName(), err.get().objectName(),
                        err.get().message());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("[{}] done", this.BUCKET_VOUCHERS);
    }
}