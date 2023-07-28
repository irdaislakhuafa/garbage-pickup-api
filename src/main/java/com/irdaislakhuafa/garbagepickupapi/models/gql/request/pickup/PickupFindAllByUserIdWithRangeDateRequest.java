package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PickupFindAllByUserIdWithRangeDateRequest {
    private String userId;
    private String start;
    private String end;

    @Builder.Default
    private List<PickupStatus> statuses = new ArrayList<>();
}