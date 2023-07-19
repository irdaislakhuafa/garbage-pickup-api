package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

public interface VoucherService extends EntityConverterService<Voucher, VoucherRequest, VoucherUpdateRequest>, CRUDService<Voucher, String> {
}