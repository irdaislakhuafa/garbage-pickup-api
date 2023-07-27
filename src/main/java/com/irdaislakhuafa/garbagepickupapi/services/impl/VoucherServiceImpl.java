package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import com.irdaislakhuafa.garbagepickupapi.services.VoucherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final UserService userService;
    private final MinIOFileService minIOFileService;

    @Value(value = "${minio.buckets.vouchers}")
    private String BUCKET_VOUCHERS;

    @Override
    public Optional<Voucher> save(Voucher request) {
        try {
            request.setCreatedBy(this.userService.getCurrentUser().getId());
            request.setCreatedAt(LocalDateTime.now());

            final var result = this.voucherRepository.save(request);
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<Voucher> update(Voucher request) {
        try {
            // TODO: update field image
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return this.voucherRepository.findById(id);
    }

    @Override
    public List<Voucher> findAll() {
        final var results = this.voucherRepository.findAll();
        return results;
    }

    @Override
    @Transactional
    public Voucher fromRequestToEntity(VoucherRequest request) {
        try {
            final var result = Voucher.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .pointsNeeded(request.getPointsNeeded())
                    .type(request.getType())
                    .value(request.getValue())
                    .build();

            if (request.getImage() != null) {
                final var imageFileName = this.minIOFileService.upload(request.getImage(), this.BUCKET_VOUCHERS);
                final var imageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                        .builder()
                        .bucketName(this.BUCKET_VOUCHERS)
                        .fileName(imageFileName)
                        .build());
                result.setImage(imageLink);
            }

            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Voucher fromUpdateRequestToEntity(VoucherUpdateRequest request) {
        try {
            final var result = Voucher.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .image(request.getImage())
                    .pointsNeeded(request.getPointsNeeded())
                    .type(request.getType())
                    .value(request.getValue())
                    .build();
            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}