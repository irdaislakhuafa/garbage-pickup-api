package com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReceiptRequest {
    private ReceiptStatus status;
    private int point;
    private String pickupId;
}