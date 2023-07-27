package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher.UserVoucherFindAllByUserIdAndStatus;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;

import java.util.List;

public interface UserVoucherService extends CRUDService<UserVoucher, String> {
    List<UserVoucher> findAll(UserVoucherFindAllByUserIdAndStatus request);

    List<UserVoucher> exchange(String userId, List<String> listId);
}