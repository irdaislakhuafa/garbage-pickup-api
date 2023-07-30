package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

import java.util.List;
import java.util.Optional;

public interface ReceiptService extends CRUDService<Receipt, String>, EntityConverterService<Receipt, ReceiptRequest, ReceiptUpdateRequest> {
    Optional<Receipt> findByPickupId(String pickupId);

    List<Receipt> findAllByPickupId(String pickupId);
}