package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserVoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserVoucherServiceImpl implements UserVoucherService {
    private final UserVoucherRepository userVoucherRepository;
    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<UserVoucher> findAll(final String userId, final List<UserVoucherStatus> status) {
        try {
            // get current user
            final var currentUser = this.userService.getCurrentUser();

            // get user and throw exception if user doesn't exist
            final var user = this.userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new BadRequestException(String.format("user with id '%s' not found", userId));
            }

            // find all voucher with is deleted false
            final var listVoucher = this.voucherRepository.findAllByIsDeleted(false);
            final var listUserVoucher = this.userVoucherRepository.findAllByUserId(userId);

            // filter non existing voucher in listUserVoucher
            final Predicate<Voucher> isExists = voucher -> {
                for (UserVoucher userVoucher : listUserVoucher) {
                    if (userVoucher.getVoucher().getId().equals(voucher.getId())) {
                        return true;
                    }
                }
                return false;
            };

            final var nonExistingUserVoucher = new ArrayList<UserVoucher>();
            listVoucher.forEach(voucher -> {
                if (!isExists.test(voucher)) {
                    final var userVoucher = UserVoucher.builder()
                            .user(user.get())
                            .voucher(voucher)
                            .status(UserVoucherStatus.AVAILABLE)
                            .createdBy(currentUser.getId())
                            .createdAt(LocalDateTime.now())
                            .build();

                    nonExistingUserVoucher.add(userVoucher);
                }
            });

            // save non existing user voucher
            final var savedNonExistingUserVoucher = this.userVoucherRepository.saveAll(nonExistingUserVoucher);

            // added saved non-existing user voucher to listUserVoucher
            listUserVoucher.addAll(savedNonExistingUserVoucher);

            // if status parameter is not empty, remove each element userVoucher if status doesn't contain in status from parameter
            if (!status.isEmpty()) {
                listUserVoucher.removeIf(userVoucher -> !status.contains(userVoucher.getStatus()));
            }

            return listUserVoucher;
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