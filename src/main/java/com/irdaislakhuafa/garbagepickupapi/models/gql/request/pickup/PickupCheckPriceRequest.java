package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PickupCheckPriceRequest {
    private int weight;
    private double lat;
    private double lng;
}