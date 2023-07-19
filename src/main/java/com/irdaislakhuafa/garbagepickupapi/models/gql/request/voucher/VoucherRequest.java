package com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VoucherRequest {
    private String title;
    private String description;
    private String image;
    private int pointsNeeded;
}