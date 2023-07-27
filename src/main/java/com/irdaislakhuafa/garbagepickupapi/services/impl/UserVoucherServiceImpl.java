package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher.UserVoucherFindAllByUserIdAndStatus;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserVoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.VoucherRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import jakarta.transaction.Transactional;
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
    public List<UserVoucher> findAll(UserVoucherFindAllByUserIdAndStatus request) {
        try {
            // get current user
            final var currentUser = this.userService.getCurrentUser();

            // get user and throw exception if user doesn't exist
            final var user = this.userRepository.findById(request.getUserId());
            if (user.isEmpty()) {
                throw new BadRequestException(String.format("user with id '%s' not found", request.getUserId()));
            }

            // find all voucher with is deleted false
            final var listVoucher = this.voucherRepository.findAllByIsDeleted(false);
            final var listUserVoucher = this.userVoucherRepository.findAllByUserId(request.getUserId());

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
            if (!request.getStatuses().isEmpty()) {
                listUserVoucher.removeIf(userVoucher -> !request.getStatuses().contains(userVoucher.getStatus()));
            }

            return listUserVoucher;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * @param listId is list id voucher that will be excanged
     * @return {@code  List<UserVoucher>} the list of vouchers that were exchanged
     */
    @Override
    @Transactional
    public List<UserVoucher> exchange(String userId, List<String> listId) {
        try {
            final var listUserVoucher = this.userVoucherRepository.findAllByIdIsIn(listId);

            // throw bad request if list user voucher is not saved in db
            if (listUserVoucher.isEmpty()) {
                throw new BadRequestException("user voucher with id from list is not found");
            }

            // throw if list user voucher contains claimed user voucher
            final var listAlreadyClaimed = listUserVoucher.stream()
                    .filter(uv -> uv.getStatus().equals(UserVoucherStatus.CLAIMED))
                    .map(UserVoucher::getId)
                    .toList();

            if (!listAlreadyClaimed.isEmpty()) {
                throw new BadRequestException(String.format("user voucher with id %s already claimd", listAlreadyClaimed));
            }

            // check is user voucher with id from parameter is exists?
            final var listUnsavedId = new ArrayList<String>();
            final var listSavedId = listUserVoucher.stream().map(UserVoucher::getId).toList();
            for (var id : listId) {
                if (!listSavedId.contains(id)) {
                    listUnsavedId.add(id);
                }
            }

            // and throw exception if list unsaved user voucher is not empty
            if (!listUnsavedId.isEmpty()) {
                throw new BadRequestException("user voucher with id " + listUnsavedId + " doesn't exists");
            }

            // check point of user
            final var user = this.userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new BadRequestException(String.format("user with id '%s' not found, please register first", userId));
            }

            listUserVoucher.forEach(userVoucher -> {
                final var voucher = userVoucher.getVoucher();
                if (user.get().getPoint() >= voucher.getPointsNeeded()) {
                    user.get().setPoint(user.get().getPoint() - voucher.getPointsNeeded());
                } else {
                    throw new BadRequestException(String.format("user point is %d but the voucher with id '%s' need %d point", user.get().getPoint(), voucher.getId(), voucher.getPointsNeeded()));
                }
            });
            this.userRepository.save(user.get());

            // change status of user voucher as claimed
            listUserVoucher.forEach(userVoucher -> {
                userVoucher.setStatus(UserVoucherStatus.CLAIMED);
                userVoucher.setUpdatedAt(LocalDateTime.now());
                userVoucher.setUpdatedBy(userService.getCurrentUser().getId());
            });

            // and update it
            final var listExchanged = this.userVoucherRepository.saveAll(listUserVoucher);

            return listExchanged;
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