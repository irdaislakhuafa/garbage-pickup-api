package com.irdaislakhuafa.garbagepickupapi.models.gql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PickupCheckPriceResponse {
    private int price;
}