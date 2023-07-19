package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import com.irdaislakhuafa.garbagepickupapi.services.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final UserService userService;

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
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> findById(String s) {
        return Optional.empty();
    }
}