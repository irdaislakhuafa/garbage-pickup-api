package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.UserVoucherStatus;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;

import java.util.List;

public interface UserVoucherService extends CRUDService<UserVoucher, String> {
    List<UserVoucher> findAll(String userId, List<UserVoucherStatus> status);
}