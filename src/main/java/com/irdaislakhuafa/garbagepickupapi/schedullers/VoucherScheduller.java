package com.irdaislakhuafa.garbagepickupapi.schedullers;

import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoucherScheduller {
    private final MinIOFileService minIOFileService;
    private final VoucherRepository voucherRepository;

    @Value(value = "${minio.buckets.vouchers}")
    private String BUCKET_VOUCHERS;

    // https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
    @Scheduled(cron = "0 0 0 * * *") // at 00:00:00 each day
    @Transactional
    public void updateImagePresignedUrl() throws Exception {
        log.info("updating presigned url for image vouchers");

        // get all voucher with is deleted false
        var listVoucher = this.voucherRepository.findAllByIsDeleted(false);

        // override value of listVouchers (peek is like map but not change return result structure)
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
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }).toList();

        // save all users
        this.voucherRepository.saveAll(listVoucher);
        log.info("success update presigned url for image vouchers");
    }
}