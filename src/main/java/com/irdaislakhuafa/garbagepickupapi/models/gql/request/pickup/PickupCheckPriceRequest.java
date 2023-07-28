package com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PickupCheckPriceRequest {
    @NotNull(message = "cannot be null")
    private Integer weight;

    @NotNull(message = "cannot be null")
    private Double lat;

    @NotNull(message = "cannot be null")
    private Double lng;
}