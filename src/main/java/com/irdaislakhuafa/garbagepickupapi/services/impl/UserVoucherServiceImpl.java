package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import com.irdaislakhuafa.garbagepickupapi.repository.UserVoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserVoucherServiceImpl implements UserVoucherService {
    private final UserVoucherRepository userVoucherRepository;

    @Override
    public List<UserVoucher> findAll(String userId, UserVoucherStatus status) {
        try {
            final var result = this.userVoucherRepository.findAllByUserIdAndStatus(userId, status);
            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<UserVoucher> save(UserVoucher request) {
        return Optional.empty();
    }

    @Override
    public Optional<UserVoucher> update(UserVoucher request) {
        return Optional.empty();
    }

    @Override
    public Optional<UserVoucher> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<UserVoucher> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<UserVoucher> findAll() {
        return null;
    }
}