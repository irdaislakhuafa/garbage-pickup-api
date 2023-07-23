package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PickupActivityRequest {
    private String pickupId;
    private String description;
}