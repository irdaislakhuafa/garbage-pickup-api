package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

public interface ReceiptService extends CRUDService<Receipt, String>, EntityConverterService<Receipt, ReceiptRequest, ReceiptUpdateRequest> {
}