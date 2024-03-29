package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, String> {
    List<UserVoucher> findAllByUserIdAndStatusIn(String userId, List<UserVoucherStatus> status);

    List<UserVoucher> findAllByUserId(String userId);

    List<UserVoucher> findAllByIdIsIn(List<String> id);
}