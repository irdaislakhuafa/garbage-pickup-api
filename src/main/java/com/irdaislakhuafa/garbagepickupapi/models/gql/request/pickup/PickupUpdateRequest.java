package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PickupUpdateRequest {
    private String id;
    private String userId;
    private PickupStatus status;
    private String place;
    private String trashTypeId;
    private int weight;
    private String description;
    private double lat;
    private double lng;
    private boolean isDeleted;
}